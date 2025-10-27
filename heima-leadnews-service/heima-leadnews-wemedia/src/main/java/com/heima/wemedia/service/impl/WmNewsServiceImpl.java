package com.heima.wemedia.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.WemediaConstants;
import com.heima.common.constants.WmNewsMessageConstants;
import com.heima.common.exception.CustomException;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.dtos.WmNewsUpOrDownDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.model.wemedia.vos.WmNewsVO;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import com.heima.wemedia.service.WmNewsService;
import com.heima.wemedia.service.WmNewsTaskService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsServiceImpl  extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {
    @Resource
    private WmNewsMapper wmNewsMapper;
    @Resource
    private WmNewsMaterialMapper wmNewsMaterialMapper;
    @Resource
    private WmMaterialMapper wmMaterialMapper;
    @Resource
    private WmUserMapper wmUserMapper;

    @Resource
    private WmNewsAutoScanService wmNewsAutoScanService;
    @Resource
    private WmNewsTaskService wmNewsTaskService;
    @Resource
    private WmNewsAutoScanService autoScanService;

    @Override
    public ResponseResult customList(WmNewsPageReqDto dto) {
        //1.分页功能：
        if( dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        dto.checkParam();
        IPage page = new Page<>(dto.getPage(), dto.getSize());

        //2.构建查询参数:
        LambdaQueryWrapper<WmNews> lq = new LambdaQueryWrapper();
        lq.eq( dto.getStatus() != null, WmNews::getStatus, dto.getStatus());
        lq.gt( dto.getBeginPubDate() != null, WmNews::getSubmitedTime, dto.getBeginPubDate());
        lq.lt( dto.getEndPubDate() != null, WmNews::getSubmitedTime, dto.getEndPubDate());
        lq.eq( dto.getChannelId() != null, WmNews::getChannelId, dto.getChannelId());
        lq.like( dto.getKeyword() != null, WmNews::getTitle, dto.getKeyword());
        lq.orderByDesc(WmNews::getCreatedTime);

        //3.执行查询：
        page = page( page, lq);
        ResponseResult result = new PageResponseResult((int) page.getCurrent(), (int) page.getSize(), (int)page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * 发布修改文章或保存为草稿
     * @param dto
     * @return
     */
    @GlobalTransactional(name="submit-WmNews", rollbackFor = Exception.class)
    @Override
    public ResponseResult submit(WmNewsDto dto){

        //0.条件判断
        if(dto == null || dto.getContent() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //1.保存或修改文章

        WmNews wmNews = new WmNews();
        //属性拷贝 属性名词和类型相同才能拷贝
        try {
            BeanUtils.copyProperties(wmNews, dto);
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        //封面图片  list---> string
        if(dto.getImages() != null && dto.getImages().size() > 0){
            //[1dddfsd.jpg,sdlfjldk.jpg]-->   1dddfsd.jpg,sdlfjldk.jpg
            String imageStr = StringUtils.join(dto.getImages(), ",");
            wmNews.setImages(imageStr);
        }
        //如果当前封面类型为自动 -1
        if(dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
            wmNews.setType(null);
        }

        saveOrUpdateWmNews(wmNews);

        //2.判断是否为草稿  如果为草稿结束当前方法
        if(dto.getStatus().equals(WmNews.Status.NORMAL.getCode())){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        //3.不是草稿，保存文章内容图片与素材的关系
        //获取到文章内容中的图片信息
        List<String> materials =  ectractUrlInfo(dto.getContent());
        saveRelativeInfoForContent(materials,wmNews.getId());

        //4.不是草稿，保存文章封面图片与素材的关系，如果当前布局是自动，需要匹配封面图片
        saveRelativeInfoForCover(dto,wmNews,materials);

        //5. 提交审核：

        Integer id = wmNews.getId();

        // 事务提交后再异步执行审核
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //审核文章
                //        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
                wmNewsTaskService.addNewsToTask(wmNews.getId(),wmNews.getPublishTime());
            }
        });


        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Override
    public ResponseResult downOrUp(WmNewsUpOrDownDto dto) {
        if(dto.getId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"文章Id不可缺少");

        WmNews wmNews = getById(dto.getId());
        if(wmNews == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章不存在");

        if(!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode()))
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"当前文章不是发布状态，不能上下架");

        //更新表wmNews
        wmNews.setEnable(dto.getEnable());
        updateById(wmNews);

        //4.修改文章enable
        if(dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2){
            update(Wrappers.<WmNews>lambdaUpdate().set(WmNews::getEnable,dto.getEnable())
                    .eq(WmNews::getId,wmNews.getId()));
        }
        //发送消息，通知article端修改文章配置
        if(wmNews.getArticleId() != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("articleId", wmNews.getArticleId());
            map.put("enable", dto.getEnable());
            kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC, JSON.toJSONString(map));
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    /**
     * 第一个功能：如果当前封面类型为自动，则设置封面类型的数据
     * 匹配规则：
     * 1，如果内容图片大于等于1，小于3  单图  type 1
     * 2，如果内容图片大于等于3  多图  type 3
     * 3，如果内容没有图片，无图  type 0
     *
     * 第二个功能：保存封面图片与素材的关系
     * @param dto
     * @param wmNews
     * @param materials
     */
    private void saveRelativeInfoForCover(WmNewsDto dto, WmNews wmNews, List<String> materials) {

        List<String> images = dto.getImages();

        //如果当前封面类型为自动，则设置封面类型的数据
        if(dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
            //多图
            if(materials.size() >= 3){
                wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            }else if(materials.size() >= 1 && materials.size() < 3){
                //单图
                wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            }else {
                //无图
                wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }

            //修改文章
            if(images != null && images.size() > 0){
                wmNews.setImages(StringUtils.join(images,","));
            }
            updateById(wmNews);
        }
        if(images != null && images.size() > 0){
            saveRelativeInfo(images,wmNews.getId(),WemediaConstants.WM_COVER_REFERENCE);
        }

    }


    /**
     * 处理文章内容图片与素材的关系
     * @param materials
     * @param newsId
     */
    private void saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        saveRelativeInfo(materials,newsId,WemediaConstants.WM_CONTENT_REFERENCE);
    }

    /**
     * 保存文章图片与素材的关系到数据库中
     * @param materials
     * @param newsId
     * @param type
     */
    private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        if(materials!=null && !materials.isEmpty()){
            //通过图片的url查询素材的id
            List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));

            //判断素材是否有效
            if(dbMaterials==null || dbMaterials.size() == 0){
                //手动抛出异常   第一个功能：能够提示调用者素材失效了，第二个功能，进行数据的回滚
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }

            if(materials.size() != dbMaterials.size()){
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }

            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());

            //批量保存
            wmNewsMaterialMapper.saveRelations(idList,newsId,type);
        }

    }


    /**
     * 提取文章内容中的图片信息
     * @param content
     * @return
     */
    private List<String> ectractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();

        List<Map> maps = JSON.parseArray(content, Map.class);
        for (Map map : maps) {
            if(map.get("type").equals("image")){
                String imgUrl = (String) map.get("value");
                materials.add(imgUrl);
            }
        }

        return materials;
    }

    /**
     * 保存或修改文章
     * @param wmNews
     */
    private void saveOrUpdateWmNews(WmNews wmNews) {
        //补全属性
        wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short)1);//默认上架

        if(wmNews.getId() == null){
            //保存
            save(wmNews);
        }else {
            //修改
            //删除文章图片与素材的关系
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId,wmNews.getId()));
            updateById(wmNews);
        }

    }

    @Override
    public ResponseResult listVO(NewsAuthDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        dto.checkPage();

        //分页参数
        IPage<WmNews> pageResult = new Page<>(dto.getPage(),dto.getSize());

        //条件查询参数
        LambdaQueryWrapper<WmNews> lq = new LambdaQueryWrapper<>();

        lq.eq(dto.getStatus() != null,WmNews::getStatus,dto.getStatus());
        lq.like(StringUtils.isNotBlank(dto.getTitle()),WmNews::getTitle,dto.getTitle());
        lq.orderByDesc(WmNews::getCreatedTime);

        pageResult = page( pageResult, lq);

        //构建额外添加authorName的实体集合
        List<WmNewsVO> wmNewsVOS = pageResult.getRecords().stream().map(wmNews -> {
            WmNewsVO wmNewsVO = new WmNewsVO();
            try {
                BeanUtils.copyProperties( wmNewsVO, wmNews);
                //在wm_user表中查询出name
                WmUser wmUser = wmUserMapper.selectById(wmNewsVO.getUserId());
                wmNewsVO.setAuthorName(wmUser.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wmNewsVO;
        }).collect(Collectors.toList());

        //构建返回参数
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int)pageResult.getTotal());
        pageResponseResult.setData(wmNewsVOS);

        return  pageResponseResult;
    }

    @Override
    public ResponseResult oneVO(Integer id) {
        if(id == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);


       WmNews wmNews = getById(id);
       if(wmNews == null)
           return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
       WmNewsVO wmNewsVO = new WmNewsVO();
       try {
           BeanUtils.copyProperties( wmNewsVO, wmNews);
           //在wm_user表中查询出name
           WmUser wmUser = wmUserMapper.selectById(wmNewsVO.getUserId());
           wmNewsVO.setAuthorName(wmUser.getName());
       } catch (Exception e) {
           e.printStackTrace();
       }

       return ResponseResult.okResult(wmNewsVO);
    }

    @Override
    public ResponseResult updateStatus(NewsAuthDto dto, Short reviewStatus) {
        if(dto == null || dto.getId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        WmNews wmNews = getById(dto.getId());
        if(wmNews == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);

        if(wmNews.getStatus() != WemediaConstants.WM_NEWS_TO_PERSON_REVIEW)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_STATUS_NOT_ALLOW);

        Short status = null;
        String reason = null;
        if(reviewStatus == WemediaConstants.WM_NEWS_REVIEW_PASS){
            status = WemediaConstants.WM_NEWS_REVIEW_PASS;
            reason = "人工审核通过";
            //填入App端相关文章信息
            ResponseResult responseResult = autoScanService.saveAppArticle(wmNews);
            if(!responseResult.getCode().equals(200)){
                throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
            }
        }else if(reviewStatus == WemediaConstants.WM_NEWS_REVIEW_FAIL){
            status = WemediaConstants.WM_NEWS_REVIEW_FAIL;
            reason = dto.getMsg();
        }

        wmNews.setStatus(status);
        wmNews.setReason(reason);

        //更新
        updateById(wmNews);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }


}

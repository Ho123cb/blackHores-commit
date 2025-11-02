package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ArticleFreemarkerService;
import com.heima.article.service.IApArticleService;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.mess.ArticleVisitStreamMess;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.vos.ApArticleVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * <p>
 * 文章信息表，存储已发布的文章 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
@Transactional
@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements IApArticleService {
    @Resource
    private ApArticleMapper apArticleMapper;
    @Resource
    private ApArticleContentMapper apArticleContentMapper;
    @Resource
    private ApArticleConfigMapper apArticleConfigMapper;

    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;
    @Autowired
    private CacheService cacheService;


    /**
     * 根据参数加载文章列表
     * @param loadtype 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    @Override
    public ResponseResult load( Short loadtype, ArticleHomeDto dto) {
        //1.校验参数
        Integer size = dto.getSize();
        if(size == null || size == 0){
            size = 10;
        }
        size = Math.min(size,MAX_PAGE_SIZE);
        dto.setSize(size);

        //类型参数检验
        if(!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_MORE)&&!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            loadtype = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        //文章频道校验
        if(StringUtils.isEmpty(dto.getTag())){
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        //时间校验
        if(dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if(dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());
        //2.查询数据
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(dto, loadtype);

        //3.结果封装
        ResponseResult responseResult = ResponseResult.okResult(apArticles);
        return responseResult;
    }

    /**
     * 审核完毕后保存文章方法
     * 实现步骤：
     * 1. 判断参数是否有效
     * 2. 根据文章id查询文章信息
     * 3. 如果存在：
     *   1. 更新ap_article信息
     *   2. 更新ap_article_content文章内容
     * 4. 如果不存在：
     *   1. 向表ap_article插入内容
     *   2. 向表ap_article_content插入内容
     *   3. 向表ap_article_config插入内容
     * 5. 构建返回数据
     * @param dto
     * @return
     */
    @Override
    public ResponseResult customSave(ArticleDto dto)   {

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//
//        }
        //1. 判断参数是否有效
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"参数失效");
        }



        //2. 根据文章id查询文章信息
        Long articleId = dto.getId();
        ApArticle apArticle = null;
        if(articleId != null) {
            apArticle = apArticleMapper.selectById(articleId);
        }
        Boolean flag = apArticle != null; // true -> 存在 ;false -> 不存在

        //3. 如果存在：
        if(flag){
            //   1. 更新ap_article信息
            apArticleMapper.updateById(apArticle);
            //   2. 更新ap_article_content文章内容
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setId(articleId);
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);

        } else {
        //4. 如果不存在：
            try {
                //   1. 向表ap_article插入内容
                apArticle = new ApArticle();
                BeanUtils.copyProperties(apArticle, dto);
                save(apArticle);
                //   2. 向表ap_article_content插入内容
                ApArticleContent apArticleContent = new ApArticleContent();
                apArticleContent.setArticleId(apArticle.getId());
                apArticleContent.setContent(dto.getContent());
                apArticleContentMapper.insert(apArticleContent);
                //   3. 向表ap_article_config插入内容
                ApArticleConfig apArticleConfig = new ApArticleConfig(apArticle.getId());
                apArticleConfigMapper.insert(apArticleConfig);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        final ApArticle ap = new ApArticle();
        try {
            BeanUtils.copyProperties(ap, dto);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        articleFreemarkerService.buildArticleToMinIO(ap,dto.getContent());

        //5. 构建返回数据
        return ResponseResult.okResult(apArticle.getId());
    }

    @Resource
    private ArticleFreemarkerService articleFreemarkerService;
    /**
     * 保存app端相关文章
     * @param dto
     * @return
     */


    @Override
    public ResponseResult load2(Short loadtype, ArticleHomeDto dto, Boolean firstPage) {
        //通过缓存中获取
        if(firstPage) {
            String key = ArticleConstants.HOT_ARTICLE_FIRST_PAGE +
                    (NumberUtils.isNumber(dto.getTag())? dto.getTag():ArticleConstants.DEFAULT_TAG);
            String resultStr = cacheService.get(key);
            if(StringUtils.isEmpty(resultStr))
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            List<ApArticle> apArticles = JSON.parseArray(resultStr, ApArticle.class);
            return ResponseResult.okResult(apArticles);
        }

        return load(loadtype, dto);
    }

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     * @param mess
     */
    @Override
    public void updateScore(ArticleVisitStreamMess mess) {
        //1.更新文章的阅读、点赞、收藏、评论的数量
        ApArticle apArticle = updateArticle(mess);
        //2.计算文章的分值
        Integer score = computeScore(apArticle);
        score = score * 3;

        //3.替换当前文章对应频道的热点数据
        replaceDataToRedis(apArticle, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + apArticle.getChannelId());

        //4.替换推荐对应的热点数据
        replaceDataToRedis(apArticle, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);

    }

    /**
     * 替换数据并且存入到redis
     * @param apArticle
     * @param score
     * @param s
     */
    private void replaceDataToRedis(ApArticle apArticle, Integer score, String s) {
        String articleListStr = cacheService.get(s);
        if (StringUtils.isNotBlank(articleListStr)) {
            List<ApArticleVo> hotArticleVoList = JSON.parseArray(articleListStr, ApArticleVo.class);

            boolean flag = true;

            //如果缓存中存在该文章，只更新分值
            for (ApArticleVo hotArticleVo : hotArticleVoList) {
                if (hotArticleVo.getId().equals(apArticle.getId())) {
                    hotArticleVo.setScore(score);
                    flag = false;
                    break;
                }
            }

            //如果缓存中不存在，查询缓存中分值最小的一条数据，进行分值的比较，如果当前文章的分值大于缓存中的数据，就替换
            try {
                if (flag) {
                    if (hotArticleVoList.size() >= 30) {
                        hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(ApArticleVo::getScore).reversed()).collect(Collectors.toList());
                        ApArticleVo lastHot = hotArticleVoList.get(hotArticleVoList.size() - 1);
                        if (lastHot.getScore() < score) {
                            hotArticleVoList.remove(lastHot);
                            ApArticleVo hot = new ApArticleVo();
                            BeanUtils.copyProperties(apArticle, hot);
                            hot.setScore(score);
                            hotArticleVoList.add(hot);
                        }


                    } else {
                        ApArticleVo hot = new ApArticleVo();
                        BeanUtils.copyProperties(apArticle, hot);
                        hot.setScore(score);
                        hotArticleVoList.add(hot);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //缓存到redis
            hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(ApArticleVo::getScore).reversed()).collect(Collectors.toList());
            cacheService.set(s, JSON.toJSONString(hotArticleVoList));

        }
    }

    /**
     * 更新文章行为数量
     * @param mess
     */
    private ApArticle updateArticle(ArticleVisitStreamMess mess) {
        ApArticle apArticle = getById(mess.getArticleId());
        apArticle.setCollection(apArticle.getCollection()==null?0:apArticle.getCollection()+mess.getCollect());
        apArticle.setComment(apArticle.getComment()==null?0:apArticle.getComment()+mess.getComment());
        apArticle.setLikes(apArticle.getLikes()==null?0:apArticle.getLikes()+mess.getLike());
        apArticle.setViews(apArticle.getViews()==null?0:apArticle.getViews()+mess.getView());
        updateById(apArticle);
        return apArticle;

    }

    /**
     * 计算文章的具体分值
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        Integer score = 0;
        if(apArticle.getLikes() != null){
            score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if(apArticle.getViews() != null){
            score += apArticle.getViews();
        }
        if(apArticle.getComment() != null){
            score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if(apArticle.getCollection() != null){
            score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }

        return score;
    }
}

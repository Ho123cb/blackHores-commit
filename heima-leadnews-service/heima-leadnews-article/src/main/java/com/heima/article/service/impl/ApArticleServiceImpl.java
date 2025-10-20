package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ArticleFreemarkerService;
import com.heima.article.service.IApArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

        //异步调用 生成静态文件上传到minio中
        articleFreemarkerService.buildArticleToMinIO(apArticle,dto.getContent());
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

}

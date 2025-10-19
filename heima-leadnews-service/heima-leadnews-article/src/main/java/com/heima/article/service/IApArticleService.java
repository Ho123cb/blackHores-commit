package com.heima.article.service;

import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
public interface IApArticleService extends IService<ApArticle> {

    /**
     * 加载首页
     * @param dto
     * @return
     */
    ResponseResult load( Short loadtype, ArticleHomeDto dto);

    /**
     * 自定义保存文章方法
     * @param dto
     * @return
     */
    ResponseResult customSave(ArticleDto dto);
}

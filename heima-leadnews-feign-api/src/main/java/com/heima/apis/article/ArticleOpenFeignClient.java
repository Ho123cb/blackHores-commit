package com.heima.apis.article;

import com.heima.apis.article.fallback.ArticleFallbackFactory;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.CommentManageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "leadnews-article", fallbackFactory = ArticleFallbackFactory.class)
public interface ArticleOpenFeignClient {
    /**
     * 自定义保存文章方法
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/article/save")
    public ResponseResult custoSave(@RequestBody ArticleDto dto);

    /**
     * 用于测试seata
     */
    @GetMapping("/api/v1/article/saves")
    public ResponseResult saves();

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    @GetMapping("/api/v1/article/{id}")
    public ApArticle findOne(@PathVariable("id") Long id) ;

    @PostMapping("/api/v1/article/manage/list")
    public ResponseResult findNewsComments(@RequestBody CommentManageDto dto);

}

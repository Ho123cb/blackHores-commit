package com.heima.article.service;

public interface ArticleHotJobService {
    /**
     * 计算文章的热度并存储到redis
     */
    void hotArticle();
}

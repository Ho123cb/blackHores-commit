package com.heima.model.article.vos;

import com.heima.model.article.pojos.ApArticle;
import lombok.Data;

import java.util.Optional;

@Data
public class ApArticleVo extends ApArticle {
    private Integer score;

    /**
     * 计算分数
     */
    public void calculateScore() {
        Integer base = Optional.ofNullable(score).orElse(0);
        base += Optional.ofNullable(getViews()).orElse(0);
        base += Optional.ofNullable(getLikes()).orElse(0) * 3;
        base += Optional.ofNullable(getComment()).orElse(0) * 5;
        base += Optional.ofNullable(getCollection()).orElse(0) * 8;
        score = base;
    }
}

package com.heima.article.service;

import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ArticleCollectionSerivce {
    /**
     * 收藏文章实现
     * @param dto
     * @return
     */
    ResponseResult collectionBehavior(CollectionBehaviorDto dto);
}

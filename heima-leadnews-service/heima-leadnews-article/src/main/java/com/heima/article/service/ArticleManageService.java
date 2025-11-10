package com.heima.article.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.CommentManageDto;

public interface ArticleManageService {
    /**
     * 查询信息列表
     * @return
     */
    ResponseResult list(CommentManageDto dto);
}

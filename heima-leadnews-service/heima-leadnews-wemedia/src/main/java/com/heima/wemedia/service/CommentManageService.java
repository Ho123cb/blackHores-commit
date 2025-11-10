package com.heima.wemedia.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.CommentManageDto;

public interface CommentManageService {
    /**
     * 查询评论列表
     * @param dto
     * @return
     */
    ResponseResult findNewsComments(CommentManageDto dto);
}

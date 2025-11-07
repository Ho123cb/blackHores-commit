package com.heima.comment.service;

import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.common.dtos.ResponseResult;

public interface CommentService {
    /**
     * 用于保存发布的评论
     * @param dto
     * @return
     */
    ResponseResult save(CommentSaveDto dto);

    /**
     * 评论点赞
     * @param dto
     * @return
     */
    ResponseResult like(CommentLikeDto dto);

    /**
     * 查询评论
     * @param dto
     * @return
     */
    ResponseResult list( CommentDto dto);
}

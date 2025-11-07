package com.heima.comment.service;

import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.common.dtos.ResponseResult;

public interface CommentReplyService {
    /**
     * 评论回复保存
     * @param dto
     * @return
     */
    ResponseResult save(CommentRepaySaveDto dto);

    /**
     * 评论回复点赞
     * @param dto
     * @return
     */
    ResponseResult like(CommentRepayLikeDto dto);

    /**
     * 评论回复列表查询
     * @param dto
     * @return
     */
    ResponseResult list(CommentRepayDto dto);
}

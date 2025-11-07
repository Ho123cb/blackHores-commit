package com.heima.model.comment.dtos;

import lombok.Data;

@Data
public class CommentLikeDto {
    //评论id
    private String commentId;
    //操作 0:点赞 1:取消点赞
    private Short operation;
}

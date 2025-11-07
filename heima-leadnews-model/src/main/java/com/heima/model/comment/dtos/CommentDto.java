package com.heima.model.comment.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    //文章id
    private String articleId;
    private Date minDate;
}

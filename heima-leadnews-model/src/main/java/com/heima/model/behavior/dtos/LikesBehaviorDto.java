package com.heima.model.behavior.dtos;

import lombok.Data;

@Data
public class LikesBehaviorDto {
    //文章id
    private long articleId;
    //操作类型 0 点赞   1 取消点赞
    private short operation;
    //0文章  1动态   2评论
    private short type;
}

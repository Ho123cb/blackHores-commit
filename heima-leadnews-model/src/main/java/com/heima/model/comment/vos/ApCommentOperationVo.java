package com.heima.model.comment.vos;

import com.heima.model.comment.pojos.ApComment;
import lombok.Data;

@Data
public class ApCommentOperationVo extends ApComment {
    //表示是否点赞了该评论， 0为点 1为未点
    private Short operation;
}

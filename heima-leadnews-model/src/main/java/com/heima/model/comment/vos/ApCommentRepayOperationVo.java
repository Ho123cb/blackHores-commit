package com.heima.model.comment.vos;

import com.heima.model.comment.pojos.ApCommentRepay;
import lombok.Data;

@Data
public class ApCommentRepayOperationVo extends ApCommentRepay {
    // 1 代表当前用户未点赞 0 代表当前用户已点赞
    private Short operation;
}

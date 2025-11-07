package com.heima.model.comment.pojos;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("ap_comment_repay_like")
public class ApCommentRepayLike {
    private static final long serialVersionUID = 1L;
    private String id;
    private long authorId;
    private String commentRepayId;
}

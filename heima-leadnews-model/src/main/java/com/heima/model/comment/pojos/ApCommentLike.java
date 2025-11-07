package com.heima.model.comment.pojos;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.sql.DataSourceDefinitions;

/**
 * 评论点赞表
 */
@Data
@Document("ap_comment_like")
public class ApCommentLike {
    private static final long serialVersionUID = 1L;
    private String id;
    private long authorId;
    private String commentId;
}

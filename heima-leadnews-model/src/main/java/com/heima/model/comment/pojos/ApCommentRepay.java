package com.heima.model.comment.pojos;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * 评论回复表
 */
@Data
@Document("ap_comment_repay")
public class ApCommentRepay {
    public  ApCommentRepay() {
        Instant instant = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        createdTime = new Date();
        updatedTime = new Date();
        likes = 0;
    }

    private static final long serialVersionUID = 1L;
    private String id;
    private long authorId;
    private String authorName;
    private String commentId;
    private String content;
    private Date createdTime;
    private long likes;
    private Date updatedTime;
}

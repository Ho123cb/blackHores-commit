package com.heima.model.comment.pojos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p>
 * 文章评论表
 * </p>
 *
 * @author finn
 */
@Data
@Document("ap_comment")
public class ApComment implements Serializable {

    private static final long serialVersionUID = 1L;

    public ApComment() {

        likes = 0;
        reply = 0;
        flag = 0;
        Instant instant = Instant.now();
        instant.atZone(ZoneId.systemDefault());
        createdTime = Date.from(instant);
    }

    private String id;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 作者名字
     */
    private String authorName;

    /**
     * 文章id
     */
    private String entryId;

    /**
     * 类型
     */
    private Short type;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likes;
    /**
     * 回复数
     */
    private Integer reply;

    /**
     *
     */
    private Short flag;

    /**
     * 创建时间
     */
    private Date createdTime;

}
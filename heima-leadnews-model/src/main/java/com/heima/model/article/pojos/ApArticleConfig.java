package com.heima.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * APP已发布文章配置表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
@Data
@NoArgsConstructor
@TableName("ap_article_config")
public class ApArticleConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    public ApArticleConfig(Long articleId){
        this.articleId = articleId;
        this.isComment = (byte)1;
        this.isForward = (byte)1;
        this.isDelete = (byte)0;
        this.isDown = (byte)0;
    }

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 是否可评论
     */
    private Byte isComment;

    /**
     * 是否转发
     */
    private Byte isForward;

    /**
     * 是否下架
     */
    private Byte isDown;

    /**
     * 是否已删除
     */
    private Byte isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Byte getIsComment() {
        return isComment;
    }

    public void setIsComment(Byte isComment) {
        this.isComment = isComment;
    }

    public Byte getIsForward() {
        return isForward;
    }

    public void setIsForward(Byte isForward) {
        this.isForward = isForward;
    }

    public Byte getIsDown() {
        return isDown;
    }

    public void setIsDown(Byte isDown) {
        this.isDown = isDown;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "ApArticleConfig{" +
            "id = " + id +
            ", articleId = " + articleId +
            ", isComment = " + isComment +
            ", isForward = " + isForward +
            ", isDown = " + isDown +
            ", isDelete = " + isDelete +
            "}";
    }
}

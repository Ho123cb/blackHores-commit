package com.heima.model.common.pojos.heima-leadnews-model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 自媒体图文内容信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-15
 */
@TableName("wm_news")
public class WmNews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 自媒体用户ID
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图文内容
     */
    private String content;

    /**
     * 文章布局	            0 无图文章	            1 单图文章	            3 多图文章
     */
    private Byte type;

    /**
     * 图文频道ID
     */
    private Integer channelId;

    private String labels;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 提交时间
     */
    private LocalDateTime submitedTime;

    /**
     * 当前状态	            0 草稿	            1 提交（待审核）	            2 审核失败	            3 人工审核	            4 人工审核通过	            8 审核通过（待发布）	            9 已发布
     */
    private Byte status;

    /**
     * 定时发布时间，不定时则为空
     */
    private LocalDateTime publishTime;

    /**
     * 拒绝理由
     */
    private String reason;

    /**
     * 发布库文章ID
     */
    private Long articleId;

    /**
     * //图片用逗号分隔
     */
    private String images;

    private Byte enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getSubmitedTime() {
        return submitedTime;
    }

    public void setSubmitedTime(LocalDateTime submitedTime) {
        this.submitedTime = submitedTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "WmNews{" +
            "id = " + id +
            ", userId = " + userId +
            ", title = " + title +
            ", content = " + content +
            ", type = " + type +
            ", channelId = " + channelId +
            ", labels = " + labels +
            ", createdTime = " + createdTime +
            ", submitedTime = " + submitedTime +
            ", status = " + status +
            ", publishTime = " + publishTime +
            ", reason = " + reason +
            ", articleId = " + articleId +
            ", images = " + images +
            ", enable = " + enable +
            "}";
    }
}

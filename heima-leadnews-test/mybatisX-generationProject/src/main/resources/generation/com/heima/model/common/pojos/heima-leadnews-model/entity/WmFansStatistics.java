package com.heima.model.common.pojos.heima-leadnews-model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 自媒体粉丝数据统计表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-15
 */
@TableName("wm_fans_statistics")
public class WmFansStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主账号ID
     */
    private Integer userId;

    /**
     * 子账号ID
     */
    private Integer article;

    private Integer readCount;

    private Integer comment;

    private Integer follow;

    private Integer collection;

    private Integer forward;

    private Integer likes;

    private Integer unlikes;

    private Integer unfollow;

    private String burst;

    /**
     * 创建时间
     */
    private LocalDate createdTime;

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

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getUnlikes() {
        return unlikes;
    }

    public void setUnlikes(Integer unlikes) {
        this.unlikes = unlikes;
    }

    public Integer getUnfollow() {
        return unfollow;
    }

    public void setUnfollow(Integer unfollow) {
        this.unfollow = unfollow;
    }

    public String getBurst() {
        return burst;
    }

    public void setBurst(String burst) {
        this.burst = burst;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "WmFansStatistics{" +
            "id = " + id +
            ", userId = " + userId +
            ", article = " + article +
            ", readCount = " + readCount +
            ", comment = " + comment +
            ", follow = " + follow +
            ", collection = " + collection +
            ", forward = " + forward +
            ", likes = " + likes +
            ", unlikes = " + unlikes +
            ", unfollow = " + unfollow +
            ", burst = " + burst +
            ", createdTime = " + createdTime +
            "}";
    }
}

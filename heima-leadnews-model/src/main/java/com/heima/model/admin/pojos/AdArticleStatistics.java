package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章数据统计表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@TableName("ad_article_statistics")
public class AdArticleStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 主账号ID
     */
    private Integer articleWeMedia;

    /**
     * 子账号ID
     */
    private Integer articleCrawlers;

    /**
     * 频道ID
     */
    private Integer channelId;

    /**
     * 草读量
     */
    private Integer read20;

    /**
     * 读完量
     */
    private Integer read100;

    /**
     * 阅读量
     */
    private Integer readCount;

    /**
     * 评论量
     */
    private Integer comment;

    /**
     * 关注量
     */
    private Integer follow;

    /**
     * 收藏量
     */
    private Integer collection;

    /**
     * 转发量
     */
    private Integer forward;

    /**
     * 点赞量
     */
    private Integer likes;

    /**
     * 不喜欢
     */
    private Integer unlikes;

    /**
     * unfollow
     */
    private Integer unfollow;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleWeMedia() {
        return articleWeMedia;
    }

    public void setArticleWeMedia(Integer articleWeMedia) {
        this.articleWeMedia = articleWeMedia;
    }

    public Integer getArticleCrawlers() {
        return articleCrawlers;
    }

    public void setArticleCrawlers(Integer articleCrawlers) {
        this.articleCrawlers = articleCrawlers;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getRead20() {
        return read20;
    }

    public void setRead20(Integer read20) {
        this.read20 = read20;
    }

    public Integer getRead100() {
        return read100;
    }

    public void setRead100(Integer read100) {
        this.read100 = read100;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AdArticleStatistics{" +
            "id = " + id +
            ", articleWeMedia = " + articleWeMedia +
            ", articleCrawlers = " + articleCrawlers +
            ", channelId = " + channelId +
            ", read20 = " + read20 +
            ", read100 = " + read100 +
            ", readCount = " + readCount +
            ", comment = " + comment +
            ", follow = " + follow +
            ", collection = " + collection +
            ", forward = " + forward +
            ", likes = " + likes +
            ", unlikes = " + unlikes +
            ", unfollow = " + unfollow +
            ", createdTime = " + createdTime +
            "}";
    }
}

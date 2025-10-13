package com.heima.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章信息表，存储已发布的文章
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
@TableName("ap_article")
public class ApArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章作者的ID
     */
    private Integer authorId;

    /**
     * 作者昵称
     */
    private String authorName;

    /**
     * 文章所属频道ID
     */
    private Integer channelId;

    /**
     * 频道名称
     */
    private String channelName;

    /**
     * 文章布局	            0 无图文章	            1 单图文章	            2 多图文章
     */
    private Byte layout;

    /**
     * 文章标记	            0 普通文章	            1 热点文章	            2 置顶文章	            3 精品文章	            4 大V 文章
     */
    private Byte flag;

    /**
     * 文章图片	            多张逗号分隔
     */
    private String images;

    /**
     * 文章标签最多3个 逗号分隔
     */
    private String labels;

    /**
     * 点赞数量
     */
    private Integer likes;

    /**
     * 收藏数量
     */
    private Integer collection;

    /**
     * 评论数量
     */
    private Integer comment;

    /**
     * 阅读数量
     */
    private Integer views;

    /**
     * 省市
     */
    private Integer provinceId;

    /**
     * 市区
     */
    private Integer cityId;

    /**
     * 区县
     */
    private Integer countyId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 同步状态
     */
    private Boolean syncStatus;

    /**
     * 来源
     */
    private Byte origin;

    private String staticUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Byte getLayout() {
        return layout;
    }

    public void setLayout(Byte layout) {
        this.layout = layout;
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Boolean syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Byte getOrigin() {
        return origin;
    }

    public void setOrigin(Byte origin) {
        this.origin = origin;
    }

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
    }

    @Override
    public String toString() {
        return "ApArticle{" +
            "id = " + id +
            ", title = " + title +
            ", authorId = " + authorId +
            ", authorName = " + authorName +
            ", channelId = " + channelId +
            ", channelName = " + channelName +
            ", layout = " + layout +
            ", flag = " + flag +
            ", images = " + images +
            ", labels = " + labels +
            ", likes = " + likes +
            ", collection = " + collection +
            ", comment = " + comment +
            ", views = " + views +
            ", provinceId = " + provinceId +
            ", cityId = " + cityId +
            ", countyId = " + countyId +
            ", createdTime = " + createdTime +
            ", publishTime = " + publishTime +
            ", syncStatus = " + syncStatus +
            ", origin = " + origin +
            ", staticUrl = " + staticUrl +
            "}";
    }
}

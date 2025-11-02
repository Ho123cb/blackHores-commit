package com.heima.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
@Data
@TableName("ap_article")
public class ApArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
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

package com.heima.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP文章作者信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-29
 */
@TableName("ap_author")
public class ApAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者名称
     */
    private String name;

    /**
     * 0 爬取数据	            1 签约合作商	            2 平台自媒体人	            
     */
    private Byte type;

    /**
     * 社交账号ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 自媒体账号
     */
    private Integer wmUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getWmUserId() {
        return wmUserId;
    }

    public void setWmUserId(Integer wmUserId) {
        this.wmUserId = wmUserId;
    }

    @Override
    public String toString() {
        return "ApAuthor{" +
            "id = " + id +
            ", name = " + name +
            ", type = " + type +
            ", userId = " + userId +
            ", createdTime = " + createdTime +
            ", wmUserId = " + wmUserId +
            "}";
    }
}

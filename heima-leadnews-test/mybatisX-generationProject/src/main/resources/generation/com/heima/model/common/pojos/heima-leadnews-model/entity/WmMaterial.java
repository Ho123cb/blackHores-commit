package com.heima.model.common.pojos.heima-leadnews-model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 自媒体图文素材信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-15
 */
@TableName("wm_material")
public class WmMaterial implements Serializable {

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
     * 图片地址
     */
    private String url;

    /**
     * 素材类型	            0 图片	            1 视频
     */
    private Byte type;

    /**
     * 是否收藏
     */
    private Boolean isCollection;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Boolean isCollection) {
        this.isCollection = isCollection;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "WmMaterial{" +
            "id = " + id +
            ", userId = " + userId +
            ", url = " + url +
            ", type = " + type +
            ", isCollection = " + isCollection +
            ", createdTime = " + createdTime +
            "}";
    }
}

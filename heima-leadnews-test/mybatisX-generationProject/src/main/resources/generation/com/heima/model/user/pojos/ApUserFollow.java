package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP用户关注信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-29
 */
@TableName("ap_user_follow")
public class ApUserFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 关注作者ID
     */
    private Integer followId;

    /**
     * 粉丝昵称
     */
    private String followName;

    /**
     * 关注度	            0 偶尔感兴趣	            1 一般	            2 经常	            3 高度
     */
    private Byte level;

    /**
     * 是否动态通知
     */
    private Byte isNotice;

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

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Byte isNotice) {
        this.isNotice = isNotice;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ApUserFollow{" +
            "id = " + id +
            ", userId = " + userId +
            ", followId = " + followId +
            ", followName = " + followName +
            ", level = " + level +
            ", isNotice = " + isNotice +
            ", createdTime = " + createdTime +
            "}";
    }
}

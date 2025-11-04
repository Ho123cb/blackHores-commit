package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP用户粉丝信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-29
 */
@TableName("ap_user_fan")
public class ApUserFan implements Serializable {

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
     * 粉丝ID
     */
    private Integer fansId;

    /**
     * 粉丝昵称
     */
    private String fansName;

    /**
     * 粉丝忠实度	            0 正常	            1 潜力股	            2 勇士	            3 铁杆	            4 老铁
     */
    private Byte level;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 是否可见我动态
     */
    private Byte isDisplay;

    /**
     * 是否屏蔽私信
     */
    private Byte isShieldLetter;

    /**
     * 是否屏蔽评论
     */
    private Byte isShieldComment;

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

    public Integer getFansId() {
        return fansId;
    }

    public void setFansId(Integer fansId) {
        this.fansId = fansId;
    }

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Byte getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Byte isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Byte getIsShieldLetter() {
        return isShieldLetter;
    }

    public void setIsShieldLetter(Byte isShieldLetter) {
        this.isShieldLetter = isShieldLetter;
    }

    public Byte getIsShieldComment() {
        return isShieldComment;
    }

    public void setIsShieldComment(Byte isShieldComment) {
        this.isShieldComment = isShieldComment;
    }

    @Override
    public String toString() {
        return "ApUserFan{" +
            "id = " + id +
            ", userId = " + userId +
            ", fansId = " + fansId +
            ", fansName = " + fansName +
            ", level = " + level +
            ", createdTime = " + createdTime +
            ", isDisplay = " + isDisplay +
            ", isShieldLetter = " + isShieldLetter +
            ", isShieldComment = " + isShieldComment +
            "}";
    }
}

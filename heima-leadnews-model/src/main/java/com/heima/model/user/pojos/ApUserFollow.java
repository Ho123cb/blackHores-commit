package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@TableName("ap_user_follow")
public class ApUserFollow implements Serializable {
    public ApUserFollow(Integer userId, Integer followId) {
        this.userId = userId;
        this.followId = followId;
    }

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

    public void setDefalut() {
        this.level = 0;
        this.isNotice = 1;
        this.createdTime = LocalDateTime.now();

        setDefalut();
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

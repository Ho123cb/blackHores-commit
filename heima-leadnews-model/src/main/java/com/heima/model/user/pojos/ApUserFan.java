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
 * APP用户粉丝信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-29
 */
@NoArgsConstructor
@Data
@TableName("ap_user_fan")
public class ApUserFan implements Serializable {

    public ApUserFan(Integer userId, Integer fansId) {
        this.userId = userId;
        this.fansId = fansId;
        this.setDefalut();
    }
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
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

    public void setDefalut() {
        this.level = 0;
        this.isDisplay = 0;
        this.isShieldLetter = 0;
        this.isShieldComment = 0;
        this.createdTime = LocalDateTime.now();
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

package com.heima.model.common.pojos.heima-leadnews-model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员用户信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-26
 */
@TableName("ad_user")
public class AdUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String image;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态	            0 暂时不可用	            1 永久不可用	            9 正常可用
     */
    private Byte status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime loginTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AdUser{" +
            "id = " + id +
            ", name = " + name +
            ", password = " + password +
            ", salt = " + salt +
            ", nickname = " + nickname +
            ", image = " + image +
            ", phone = " + phone +
            ", status = " + status +
            ", email = " + email +
            ", loginTime = " + loginTime +
            ", createdTime = " + createdTime +
            "}";
    }
}

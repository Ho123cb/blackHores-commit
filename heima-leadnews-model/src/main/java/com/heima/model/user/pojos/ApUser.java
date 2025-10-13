package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-12
 */
@TableName("ap_user")
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 密码、通信等加密盐
     */
    private String salt;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码,md5加密
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String image;

    /**
     * 0 男	            1 女	            2 未知
     */
    private Byte sex;

    /**
     * 0 未	            1 是
     */
    private Byte isCertification;

    /**
     * 是否身份认证
     */
    private Boolean isIdentityAuthentication;

    /**
     * 0正常	            1锁定
     */
    private Byte status;

    /**
     * 0 普通用户	            1 自媒体人	            2 大V
     */
    private Byte flag;

    /**
     * 注册时间
     */
    private LocalDateTime createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(Byte isCertification) {
        this.isCertification = isCertification;
    }

    public Boolean getIsIdentityAuthentication() {
        return isIdentityAuthentication;
    }

    public void setIsIdentityAuthentication(Boolean isIdentityAuthentication) {
        this.isIdentityAuthentication = isIdentityAuthentication;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ApUser{" +
            "id = " + id +
            ", salt = " + salt +
            ", name = " + name +
            ", password = " + password +
            ", phone = " + phone +
            ", image = " + image +
            ", sex = " + sex +
            ", isCertification = " + isCertification +
            ", isIdentityAuthentication = " + isIdentityAuthentication +
            ", status = " + status +
            ", flag = " + flag +
            ", createdTime = " + createdTime +
            "}";
    }
}

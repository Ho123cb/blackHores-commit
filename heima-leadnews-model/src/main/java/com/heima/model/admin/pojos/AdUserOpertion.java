package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员操作行为信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@TableName("ad_user_opertion")
public class AdUserOpertion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 登录设备ID
     */
    private Integer equipmentId;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录地址
     */
    private String address;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 登录时间
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

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AdUserOpertion{" +
            "id = " + id +
            ", userId = " + userId +
            ", equipmentId = " + equipmentId +
            ", ip = " + ip +
            ", address = " + address +
            ", type = " + type +
            ", description = " + description +
            ", createdTime = " + createdTime +
            "}";
    }
}

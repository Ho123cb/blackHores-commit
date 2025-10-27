package com.heima.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色权限信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@TableName("ad_role_auth")
public class AdRoleAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 资源类型	            0 菜单	            1 功能
     */
    private Byte type;

    /**
     * 资源ID
     */
    private Integer entryId;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AdRoleAuth{" +
            "id = " + id +
            ", roleId = " + roleId +
            ", type = " + type +
            ", entryId = " + entryId +
            ", createdTime = " + createdTime +
            "}";
    }
}

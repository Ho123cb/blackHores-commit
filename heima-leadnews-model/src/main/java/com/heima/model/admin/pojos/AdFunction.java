package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 页面功能信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@TableName("ad_function")
public class AdFunction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 功能代码
     */
    private String code;

    /**
     * 父功能
     */
    private Integer parentId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AdFunction{" +
            "id = " + id +
            ", name = " + name +
            ", code = " + code +
            ", parentId = " + parentId +
            ", createdTime = " + createdTime +
            "}";
    }
}

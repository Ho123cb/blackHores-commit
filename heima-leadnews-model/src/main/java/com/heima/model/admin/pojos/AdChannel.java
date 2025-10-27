package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdChannel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 频道名称
     */
    private String name;

    /**
     * 频道描述
     */
    private String description;

    /**
     * 是否默认频道
     */
    private Boolean isDefault;

    private Boolean status;

    /**
     * 默认排序
     */
    private Integer ord;

    /**
     * 创建时间
     */
    private Date createdTime;



    @Override
    public String toString() {
        return "WmChannel{" +
                "id = " + id +
                ", name = " + name +
                ", description = " + description +
                ", isDefault = " + isDefault +
                ", status = " + status +
                ", ord = " + ord +
                ", createdTime = " + createdTime +
                "}";
    }

}

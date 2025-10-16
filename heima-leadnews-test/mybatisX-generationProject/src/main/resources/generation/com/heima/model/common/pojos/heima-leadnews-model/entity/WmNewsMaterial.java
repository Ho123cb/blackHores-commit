package com.heima.model.common.pojos.heima-leadnews-model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 自媒体图文引用素材信息表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-15
 */
@TableName("wm_news_material")
public class WmNewsMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 素材ID
     */
    private Integer materialId;

    /**
     * 图文ID
     */
    private Integer newsId;

    /**
     * 引用类型	            0 内容引用	            1 主图引用
     */
    private Byte type;

    /**
     * 引用排序
     */
    private Byte ord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getOrd() {
        return ord;
    }

    public void setOrd(Byte ord) {
        this.ord = ord;
    }

    @Override
    public String toString() {
        return "WmNewsMaterial{" +
            "id = " + id +
            ", materialId = " + materialId +
            ", newsId = " + newsId +
            ", type = " + type +
            ", ord = " + ord +
            "}";
    }
}

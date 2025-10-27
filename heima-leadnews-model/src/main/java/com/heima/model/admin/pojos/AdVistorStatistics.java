package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 访问数据统计表
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@TableName("ad_vistor_statistics")
public class AdVistorStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 日活
     */
    private Integer activity;

    /**
     * 访问量
     */
    private Integer vistor;

    /**
     * IP量
     */
    private Integer ip;

    /**
     * 注册量
     */
    private Integer register;

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

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getVistor() {
        return vistor;
    }

    public void setVistor(Integer vistor) {
        this.vistor = vistor;
    }

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AdVistorStatistics{" +
            "id = " + id +
            ", activity = " + activity +
            ", vistor = " + vistor +
            ", ip = " + ip +
            ", register = " + register +
            ", createdTime = " + createdTime +
            "}";
    }
}

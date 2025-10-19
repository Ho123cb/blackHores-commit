package com.heima.wemedia.service;


import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsAutoScanService {
    /**
     * 实现审核文章功能
     * @param id
     */
    public void autoScanWmNews(Integer id);
}

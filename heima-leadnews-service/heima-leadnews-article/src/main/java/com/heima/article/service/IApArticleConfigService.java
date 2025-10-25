package com.heima.article.service;

import com.heima.model.article.pojos.ApArticleConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * APP已发布文章配置表 服务类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
public interface IApArticleConfigService extends IService<ApArticleConfig> {

    /**
     * 根据条件修改
     * @param map
     */
    void updateByMap(Map map);
}

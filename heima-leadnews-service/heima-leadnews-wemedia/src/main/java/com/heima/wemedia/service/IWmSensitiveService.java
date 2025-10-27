package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;

/**
 * <p>
 * 敏感词信息表 服务类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
public interface IWmSensitiveService extends IService<WmSensitive> {

    /**
     * 实现分页+模糊查询
     * @param dto
     * @return
     */
    ResponseResult list(SensitiveDto dto);

    /**
     * 自定义新增
     * @param dto
     * @return
     */
    ResponseResult customSave(AdSensitive dto);

    /**
     * 直接删除
     * @param id
     * @return
     */
    ResponseResult deleteById(Integer id);

    /**
     * 自定义修改
     * @param adSensitive
     * @return
     */
    ResponseResult customUpdate(AdSensitive adSensitive);
}

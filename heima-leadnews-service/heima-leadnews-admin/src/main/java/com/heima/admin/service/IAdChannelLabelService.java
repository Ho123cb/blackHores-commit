package com.heima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannelLabel;
import com.heima.model.common.dtos.ResponseResult;

/**
 * <p>
 * 频道标签信息表 服务类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
public interface IAdChannelLabelService extends IService<AdChannelLabel> {

    /**
     * 分页+模糊查询
     * @param dto
     * @return
     */
    ResponseResult list(ChannelDto dto);
}

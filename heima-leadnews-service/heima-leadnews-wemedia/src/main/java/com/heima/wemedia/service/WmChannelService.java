package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {


    /**
     * 分页+模糊查询
     * @param dto
     * @return
     */
    ResponseResult list(ChannelDto dto);

    /**
     * 自定义保存
     * @param adChannel
     * @return
     */
    ResponseResult customSave(AdChannel adChannel);

    /**
     * 通过channelId删除
     * @param id
     * @return
     */
    ResponseResult deleteById(Integer id);

    /**
     * 自定义修改
     * @param adChannel
     * @return
     */
    ResponseResult customUpdate(AdChannel adChannel);
}
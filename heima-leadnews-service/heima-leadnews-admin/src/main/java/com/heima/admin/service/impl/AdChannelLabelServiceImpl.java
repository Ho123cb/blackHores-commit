package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdChannelLabelMapper;
import com.heima.admin.service.IAdChannelLabelService;
import com.heima.apis.wemedia.IWemediaClient;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannelLabel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 频道标签信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@Service
public class AdChannelLabelServiceImpl extends ServiceImpl<AdChannelLabelMapper, AdChannelLabel> implements IAdChannelLabelService {
    @Resource
    private AdChannelLabelMapper adChannelLabelMapper;

    @Resource
    private IWemediaClient iWemediaClient;
    @Override
    public ResponseResult list(ChannelDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        //修复分页参数
        dto.checkPage();

        return  iWemediaClient.list(dto);
    }
}

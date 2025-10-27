package com.heima.wemedia.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Resource
    private WmChannelMapper wmChannelMapper;

    @Override
    public ResponseResult list(ChannelDto dto) {
        //检查参数
        if(dto == null )
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        dto.checkPage();

        //构建分页参数
        IPage<WmChannel> page = new Page<>(dto.getPage(),dto.getSize());

        //构建查询条件
        LambdaQueryWrapper<WmChannel> lq = new LambdaQueryWrapper<>();
        lq.like(StringUtils.isNotBlank(dto.getName()),WmChannel::getName,dto.getName());

        log.info("已经修改成功123~~~~");
        //执行查询
        page = page( page, lq);
        PageResponseResult pRG = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        pRG.setData(page.getRecords());
        return pRG;
    }
}
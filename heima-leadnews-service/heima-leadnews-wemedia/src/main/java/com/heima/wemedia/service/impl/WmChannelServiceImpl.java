package com.heima.wemedia.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
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

    @Override
    public ResponseResult customSave(AdChannel adChannel) {
        //检查参数
        if(adChannel == null || StringUtils.isBlank(adChannel.getName()))
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        WmChannel wmChannel = new WmChannel();

        //判断频道名称是否重复
        LambdaQueryWrapper<WmChannel> lq = new LambdaQueryWrapper<>();
        lq.eq(WmChannel::getName,adChannel.getName());
        WmChannel wmChannel1 = wmChannelMapper.selectOne(lq);
        if(wmChannel1 != null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        try {
            BeanUtils.copyProperties(wmChannel, adChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //执行保存
        save(wmChannel);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Integer id) {
        //检查参数
        if(id == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        //执行删除
        boolean result = removeById(id);
        //可能没有数据
        if(result)
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

        return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }

    @Override
    public ResponseResult customUpdate(AdChannel adChannel) {
        //检查参数
        if(adChannel == null || adChannel.getId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        WmChannel wmChannel = new WmChannel();
        try {
            BeanUtils.copyProperties(wmChannel, adChannel);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //执行修改
        boolean result = updateById(wmChannel);
        if(result)
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

        return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }
}
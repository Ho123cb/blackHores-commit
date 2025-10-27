package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.service.IWmSensitiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.common.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 敏感词信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@Service
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements IWmSensitiveService {
    @Resource
    private WmSensitiveMapper wmSensitiveMapper;

    @Override
    public ResponseResult list(SensitiveDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        dto.checkParam();

        //分页+模糊+按照创建时间倒序（从最近到以前）
        IPage<WmSensitive> pageResult = new Page<>(dto.getPage(), dto.getSize());

        LambdaQueryWrapper<WmSensitive> lq  = new LambdaQueryWrapper();
        lq.like(StringUtils.isNotBlank(dto.getName()), WmSensitive::getSensitives, dto.getName());
        lq.orderByDesc(WmSensitive::getCreatedTime);

        pageResult = page( pageResult, lq);

        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int)pageResult.getTotal());
        pageResponseResult.setData(pageResult.getRecords());

        return pageResponseResult;
    }

    @Override
    public ResponseResult customSave(AdSensitive dto) {
        if(dto == null || StringUtils.isBlank(dto.getSensitives()))
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        //查询是否重名
        LambdaQueryWrapper<WmSensitive> lq = new LambdaQueryWrapper();
        lq.eq(WmSensitive::getSensitives, dto.getSensitives());
        Integer count = wmSensitiveMapper.selectCount(lq);
        if(count > 0)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);

        //保存
        WmSensitive wmSensitive = new WmSensitive();
        try {
            BeanUtils.copyProperties(wmSensitive, dto);
            wmSensitive.setCreatedTime(new Date());
            wmSensitiveMapper.insert(wmSensitive);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Integer id) {
        if(id == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        removeById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult customUpdate(AdSensitive adSensitive) {
        if(adSensitive == null || adSensitive.getId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        //判断是否重名
        if(isNamed(adSensitive.getSensitives()))
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);

        //修改
        WmSensitive wmSensitive = new WmSensitive();
        try {
            BeanUtils.copyProperties(wmSensitive, adSensitive);
            wmSensitiveMapper.updateById(wmSensitive);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    public Boolean isNamed(String sensitives){
        //查询是否重名
        LambdaQueryWrapper<WmSensitive> lq = new LambdaQueryWrapper();
        lq.eq(WmSensitive::getSensitives, sensitives);
        Integer count = wmSensitiveMapper.selectCount(lq);
        if(count > 0)
            return true;
        return false;
    }
}



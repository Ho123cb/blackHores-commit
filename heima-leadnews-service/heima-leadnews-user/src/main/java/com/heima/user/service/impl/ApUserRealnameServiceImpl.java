package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.ApAuthConstants;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.user.mapper.ApUserRealnameMapper;
import com.heima.user.service.IApUserRealnameService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * APP实名认证信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@Service
@Slf4j
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements IApUserRealnameService {
    @Resource
    private ApUserRealnameMapper apUserRealnameMapper;

    @Override
    public ResponseResult customList(AuthDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        dto.checkPage();

        //构建分页查询相关条件
        IPage<ApUserRealname> pageResult = new Page<>( dto.getPage(), dto.getSize());

        //构建其余查询条件
        LambdaQueryWrapper<ApUserRealname> lq = new LambdaQueryWrapper<>();

        lq.orderByDesc(ApUserRealname::getCreatedTime);
        lq.eq(dto.getStatus() != null, ApUserRealname::getStatus, dto.getStatus());

        pageResult = page( pageResult, lq);
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)pageResult.getTotal());
        pageResponseResult.setData(pageResult.getRecords());

        return pageResponseResult;
    }

    @Override
    public ResponseResult updateStatus(AuthDto dto, int i) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        dto.checkPage();

        ApUserRealname apUserRealname = getById(dto.getId());

        if(i == ApAuthConstants.FAIL)
            apUserRealname.setStatus(ApAuthConstants.AUTH_FAIL);
        else if(i == ApAuthConstants.PASS)
            apUserRealname.setStatus(ApAuthConstants.AUTH_PASS);

        apUserRealname.setUpdatedTime(new Date());
        apUserRealname.setSubmitedTime(new Date());

        if(StringUtils.isNotBlank(dto.getMsg()))
            apUserRealname.setReason(dto.getMsg());

        updateById(apUserRealname);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}

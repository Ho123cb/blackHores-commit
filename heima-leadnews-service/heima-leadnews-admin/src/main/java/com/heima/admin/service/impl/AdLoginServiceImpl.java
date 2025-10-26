package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heima.admin.service.AdLoginService;
import com.heima.admin.service.IAdUserService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdLoginServiceImpl implements AdLoginService {
    @Resource
    private IAdUserService iAdUserService;

    @Override
    public ResponseResult login(AdUserDto adUserDto) {
        if(adUserDto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        QueryWrapper<AdUser> qw = new QueryWrapper<>();
        qw.eq("name",adUserDto.getName());
        AdUser user = iAdUserService.getOne(qw);

        if(user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);

        //通过加盐以及MD5处理后判断密码是否相等
        String salt = user.getSalt();
        String md5Password = DigestUtils.md5DigestAsHex((adUserDto.getPassword() + salt).getBytes());
        if(!md5Password.equals(user.getPassword()))
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);

        //准备制造token等进行返回
        Map map = new HashMap(2);

        String token = AppJwtUtil.getToken((long)user.getId());

        map.put("user",user);
        map.put("token",token);

        return ResponseResult.okResult(map);
    }
}

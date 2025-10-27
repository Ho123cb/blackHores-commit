package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.LoginDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.IApUserService;
import com.heima.utils.common.AppJwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * APP用户信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-12
 */
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements IApUserService {
    @Resource
    private ApUserMapper apUserMapper;

    @Override
    public ResponseResult loginAuth(LoginDto loginDto) {
        //1. 判断是不是游客
        String phone = loginDto.getPhone();
        String password = loginDto.getPassword();
        //1.1 判断是否为空 为空则是游客准备直接返回
        if( StringUtils.isBlank(phone) || StringUtils.isBlank(password)){
            String token = AppJwtUtil.getToken(0L);
            Map map = new HashMap(1);
            map.put("token", token);
            return ResponseResult.okResult(map);
        }
        //2. 完成密码校验逻辑
        //2.1 查询 salt
        QueryWrapper<ApUser> qw= new QueryWrapper();
        qw.eq("phone", phone);
        ApUser apUser = apUserMapper.selectOne(qw);
        if(apUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }

        String salt = apUser.getSalt();
        //2.2将盐与用户输入的密码与数据库中的密码比对
        String toBeTest = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if(!toBeTest.equals(apUser.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //3. 生成对应的token
        String token = AppJwtUtil.getToken(apUser.getId());

        //4. 返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        apUser.setPassword("");
        apUser.setSalt("");
        resultMap.put("user", apUser);

        return ResponseResult.okResult(resultMap);
    }
}

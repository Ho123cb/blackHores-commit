package com.heima.user.service;

import com.heima.model.common.dtos.LoginDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.pojos.ApUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * APP用户信息表 服务类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-12
 */
public interface IApUserService extends IService<ApUser> {

    /**
     * 用户登录业务
     * @param loginDto
     * @return
     */
    ResponseResult loginAuth(LoginDto loginDto);
}

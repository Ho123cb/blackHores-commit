package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.IAdUserService;
import com.heima.model.admin.pojos.AdUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员用户信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-26
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements IAdUserService {

}

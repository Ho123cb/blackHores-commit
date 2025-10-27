package com.heima.userservice.impl;

import com.heima.model.user.pojos.ApUserRealname;
import com.heima.mapper.ApUserRealnameMapper;
import com.heima.userservice.IApUserRealnameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP实名认证信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements IApUserRealnameService {

}

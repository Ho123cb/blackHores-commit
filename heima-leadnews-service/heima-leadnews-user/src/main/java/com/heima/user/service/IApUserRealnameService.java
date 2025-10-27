package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUserRealname;

/**
 * <p>
 * APP实名认证信息表 服务类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-27
 */
public interface IApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 用于分页+模糊查询
     * @param dto
     * @return
     */
    ResponseResult customList(AuthDto dto);

    /**
     * 修改审核的状态
     * @param dto
     * @param i
     * @return
     */
    ResponseResult updateStatus(AuthDto dto, int i);
}

package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;

public interface ApInfoQueryService {
    /**
     * 根据用户ID查询用户名
     * @param id
     * @return
     */
    ResponseResult queryName(Long id);
}

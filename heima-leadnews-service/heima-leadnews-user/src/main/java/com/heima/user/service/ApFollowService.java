package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;

public interface ApFollowService {
    /**
     * 用户端发起关注的方法
     * @param dto
     * @return
     */
    ResponseResult followByAppUser(UserRelationDto dto);
}

package com.heima.behavior.service;

import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface LikeSerivce {
    /**
     * 用于实现文章点击喜欢的存储功能
     * @param dto
     * @return
     */
    ResponseResult like(LikesBehaviorDto dto);
}

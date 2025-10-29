package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.dtos.UnLikeBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface UnLikeService {
    /**
     * 不喜欢行为记录
     * @param dto
     * @return
     */
    ResponseResult unLikeBehavior(UnLikeBehaviorDto dto);
}

package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ReadService {
    /**
     * 保存阅读行为
     * @return
     */
    ResponseResult readBehavior(ReadBehaviorDto dto);
}

package com.heima.behavior.controller.v1;

import com.heima.behavior.service.ReadService;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@ApiOperation("用户行为阅读接口")
@RestController
@RequestMapping("/api/v1/read_behavior")
public class readController {
    @Resource
    private ReadService readService;

    @ApiOperation("用户行为阅读记录")
    @PostMapping
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto dto){
        return readService.readBehavior(dto);
    }
}

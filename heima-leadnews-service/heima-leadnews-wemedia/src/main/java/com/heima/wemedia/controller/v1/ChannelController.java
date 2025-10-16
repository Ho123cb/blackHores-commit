package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.wemedia.service.WmChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("频道管理接口")
@RequestMapping("/api/v1/channel")
@RestController
@Slf4j
public class ChannelController {
    @Resource
    private WmChannelService wmChannelService;

    @ApiOperation("查询频道列表")
    @GetMapping("/channels")
    public ResponseResult channels(){
        return ResponseResult.okResult(wmChannelService.list());
    }
}

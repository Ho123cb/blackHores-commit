package com.heima.admin.controller.v1;

import com.heima.admin.service.IAdChannelLabelService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("频道管理接口")
@RestController
@RequestMapping("/api/v1/channel")
@Slf4j
public class AdChannelController {
    @Resource
    private IAdChannelLabelService adChannelLabelService;


    @ApiOperation("频道分页+模糊查询")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody ChannelDto dto) {
        return adChannelLabelService.list(dto);
    }

}

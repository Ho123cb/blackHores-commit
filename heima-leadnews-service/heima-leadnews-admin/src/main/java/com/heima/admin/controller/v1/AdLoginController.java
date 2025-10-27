package com.heima.admin.controller.v1;

import com.heima.admin.service.AdLoginService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("管理员登录接口")
@RestController
@RequestMapping("/login")
public class AdLoginController {
    @Resource
    private AdLoginService adLoginService;

    @ApiOperation("登录接口")
    @PostMapping("/in")
    public ResponseResult login(@RequestBody AdUserDto adUserDto) {
        return adLoginService.login(adUserDto);
    }
}

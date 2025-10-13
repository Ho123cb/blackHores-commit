package com.heima.appGateway.controller.v1;

import com.heima.model.common.dtos.LoginDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.appGateway.service.IApUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("用户登录")
@RequestMapping("/api/v1/login")
@RestController
@Slf4j
public class ApLoginController {
    @Resource
    private IApUserService iApUserService;

    /**
     * 返回结果填入 ApUser 对象 和 token 或者单token
     * @param loginDto
     * @return
     */
    @ApiOperation("app用户登录")
    @PostMapping("/login_auth")
    public ResponseResult loginAuth(@RequestBody LoginDto loginDto){

        ResponseResult result = iApUserService.loginAuth(loginDto);
        return result;
    }
}

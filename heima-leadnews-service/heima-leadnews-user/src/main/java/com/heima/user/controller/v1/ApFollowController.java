package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.user.service.ApFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("用户关注相关接口")
@RestController
@RequestMapping("/api/v1/user/user_follow")
public class ApFollowController {
    @Resource
    private ApFollowService apFollowService;

    @ApiOperation("关注用户方法")
    @PostMapping
    public ResponseResult followByAppUser(@RequestBody UserRelationDto dto){
        return apFollowService.followByAppUser(dto);
    }
}

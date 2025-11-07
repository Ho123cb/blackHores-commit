package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.user.service.ApFollowService;
import com.heima.user.service.ApInfoQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("用户信息查询相关接口")
@RestController
@RequestMapping("/api/v1/user/query")
public class ApInfoQueryController {
    @Resource
    private ApInfoQueryService apInfoQueryService;
    //查询用户信息
    @ApiOperation("用户名字查询")
    @GetMapping("/{id}")
    public ResponseResult queryName(@PathVariable("id") Long id){
        return apInfoQueryService.queryName(id);
    }
}

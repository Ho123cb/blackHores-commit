package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import com.heima.user.service.IApUserRealnameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("管理员用户审核接口")
@RestController
@RequestMapping("/api/v1/auth")
public class ApAuthController {
    @Resource
    private IApUserRealnameService iApUserRealnameService;

    @ApiOperation("用户审核查询")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody AuthDto dto) {
        return iApUserRealnameService.customList(dto);
    }

    @ApiOperation("审核失败")
    @PostMapping("/authFail")
    public ResponseResult authFail(@RequestBody AuthDto dto) {
        return iApUserRealnameService.updateStatus(dto, 2);
    }

    @ApiOperation("审核通过")
    @PostMapping("/authPass")
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return iApUserRealnameService.updateStatus(dto, 1);
    }
}

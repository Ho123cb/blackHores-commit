package com.heima.wemedia.controller.v1;

import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitiveDto;
import com.heima.wemedia.service.IWmSensitiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("敏感词管理接口")
@RestController
@RequestMapping("/api/v1/sensitive")
public class SensitiveController {
    @Resource
    private IWmSensitiveService iWmSensitiveService;

    @ApiOperation("敏感词列表查询")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody SensitiveDto dto){
        return iWmSensitiveService.list(dto);
    }

    @ApiOperation("新增敏感词")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AdSensitive dto){
        return iWmSensitiveService.customSave(dto);
    }


    @ApiOperation("删除敏感词")
    @DeleteMapping("/del/{id}")
    public ResponseResult del(@PathVariable("id") Integer id){
        return iWmSensitiveService.deleteById(id);
    }

    @ApiOperation("修改敏感词")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdSensitive adSensitive){
        return iWmSensitiveService.customUpdate(adSensitive);
    }
}

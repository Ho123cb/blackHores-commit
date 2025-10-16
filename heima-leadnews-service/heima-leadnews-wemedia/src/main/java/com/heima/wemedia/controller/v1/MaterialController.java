package com.heima.wemedia.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api("素材管理接口")
@RestController
@RequestMapping("/api/v1/material")
@Slf4j
public class MaterialController {
    @Resource
    private WmMaterialService wmMaterialService;


    @ApiOperation("用于上传图片")
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(@RequestBody MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @ApiOperation("用于查询图片素材列表")
    @PostMapping("/list")
    public ResponseResult uploadVideo(@RequestBody WmMaterialDto wmMaterialDto){
        return wmMaterialService.customList(wmMaterialDto);
    }
}

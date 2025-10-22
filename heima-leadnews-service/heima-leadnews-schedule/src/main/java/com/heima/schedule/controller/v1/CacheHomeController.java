package com.heima.schedule.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 完成延迟任务添加、移除、获取
 */
@Api("延迟任务相关接口~~~")
@RequestMapping("/api/v1/cache")
@RestController
@Slf4j
public class CacheHomeController {
    //添加延迟任务
    @ApiOperation("添加延迟任务")
    @PostMapping("/add")
    public ResponseResult add(){
        return null;
    }

}

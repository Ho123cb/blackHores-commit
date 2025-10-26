package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;
import com.heima.search.service.ApSearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("文章搜索历史管理")
@RestController
@RequestMapping("/api/v1/history")
@Slf4j
public class ApSearchHistoryController {
    @Resource
    private ApSearchHistoryService apSearchHistoryService;

    @ApiOperation("搜索历史加载")
    @PostMapping("/load")
    public ResponseResult load(){
        return apSearchHistoryService.load();
    }

    @ApiOperation("搜索历史删除")
    @PostMapping("/del")
    public ResponseResult del(@RequestBody HistorySearchDto dto){
        return apSearchHistoryService.del(dto);
    }
}

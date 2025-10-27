package com.heima.wemedia.controller.v1;

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.WmChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("频道管理接口")
@RequestMapping("/api/v1/channel")
@RestController
@Slf4j
public class ChannelController {
    @Resource
    private WmChannelService wmChannelService;

    @ApiOperation("查询频道列表")
    @GetMapping("/channels")
    public ResponseResult channels(){
        return ResponseResult.okResult(wmChannelService.list());
    }

    @ApiOperation("分页模糊查询")
    @PostMapping("/list")
    //TODO 分析清楚如果通过feign接口调用改变一跳和二跳请求方法会不会导致出错呢
    public ResponseResult list(@RequestBody ChannelDto dto){
        return wmChannelService.list(dto);
    }

    @ApiOperation("新增频道")
    @PostMapping("/save")
    public ResponseResult customSave(@RequestBody AdChannel adChannel){
        return wmChannelService.customSave(adChannel);
    }

    @ApiOperation("修改频道")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdChannel adChannel){
        return wmChannelService.customUpdate(adChannel);
    }

    @ApiOperation("删除频道")
    @GetMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        return wmChannelService.deleteById(id);
    }

}

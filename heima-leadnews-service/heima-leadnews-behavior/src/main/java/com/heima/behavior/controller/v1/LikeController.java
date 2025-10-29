package com.heima.behavior.controller.v1;

import com.heima.behavior.service.LikeSerivce;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@ApiOperation("点赞管理接口")
@RestController
@RequestMapping("/api/v1/likes_behavior")
public class LikeController {
    @Resource
    private LikeSerivce likeSerice;

    @ApiOperation("点赞")
    @PostMapping
    public ResponseResult like(@RequestBody LikesBehaviorDto dto){
        return likeSerice.like(dto);
    }
}

package com.heima.comment.controller.v1;

import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.comment.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("评论接口")
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @ApiOperation("发布评论")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody CommentSaveDto dto){
        return commentService.save(dto);
    }

    @ApiOperation("点赞评论")
    @PostMapping("/like")
    public ResponseResult like(@RequestBody CommentLikeDto dto){
        return commentService.like(dto);
    }

    @ApiOperation("查询评论")
    @PostMapping("/load")
    public ResponseResult list(@RequestBody CommentDto dto){
        return commentService.list(dto);
    }
}

package com.heima.comment.controller.v1;


import com.heima.comment.service.CommentReplyService;
import com.heima.model.comment.dtos.*;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("评论回复接口")
@RestController
@RequestMapping("/api/v1/comment_repay")
public class CommentReplyController {
    @Resource
    private CommentReplyService commentReplyService;

    @ApiOperation("发布评论回复")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody CommentRepaySaveDto dto) {
        return commentReplyService.save(dto);
    }

    @ApiOperation("点赞评论")
    @PostMapping("/like")
    public ResponseResult like(@RequestBody CommentRepayLikeDto dto){
        return commentReplyService.like(dto);
    }

    @ApiOperation("查询评论")
    @PostMapping("/load")
    public ResponseResult list(@RequestBody CommentRepayDto dto){
        return commentReplyService.list(dto);
    }
}

package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.CommentManageDto;
import com.heima.wemedia.service.CommentManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// /api/v1/comment/manage/find_news_comments
@Api("自媒体端管理评论接口")
@RestController
@RequestMapping("/api/v1/comment/manage")
public class CommentManageController {
    @Resource
    private CommentManageService commentManageService;

    @ApiOperation("查询评论列表")
    @PostMapping("/find_news_comments")
    public ResponseResult findNewsComments(@RequestBody CommentManageDto dto){
        return commentManageService.findNewsComments(dto);
    }
}

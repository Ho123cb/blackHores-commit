package com.heima.article.controller.v1;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.article.service.ArticleManageService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.CommentManageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("文章管理服务接口")
@RequestMapping("/api/v1/article/manage")
@RestController
public class ArticleManageController {
    @Resource
    private ArticleManageService articleManageService;

    @ApiOperation("文章信息查询")
    @PostMapping("/list")
    public ResponseResult findNewsComments(@RequestBody CommentManageDto dto) {
        return articleManageService.list(dto);
    }
}

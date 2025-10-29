package com.heima.article.controller.v1;

import com.heima.article.service.LoadArticleBehaviorSerivce;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("用户文章数据回显服务接口")
@RequestMapping("/api/v1/article")
@RestController
public class loadArticleBehaviorController {
    @Resource
    private LoadArticleBehaviorSerivce loadArticleBehaviorService;

    @ApiOperation("数据回显方法")
    @PostMapping("/load_article_behavior")
    public ResponseResult loadArticleBehavior(@RequestBody ArticleInfoDto dto){
        return loadArticleBehaviorService.loadArticleBehavior(dto);
    }
}

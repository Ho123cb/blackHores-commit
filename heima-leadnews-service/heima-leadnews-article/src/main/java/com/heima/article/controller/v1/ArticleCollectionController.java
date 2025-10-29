package com.heima.article.controller.v1;

import com.heima.article.service.ArticleCollectionSerivce;
import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("用户收藏文章服务接口")
@RequestMapping("/api/v1/article")
@RestController
public class ArticleCollectionController {
    @Resource
    private ArticleCollectionSerivce articleCollectionSerivce;

    @ApiOperation("收藏文章")
    @PostMapping("/collection_behavior")
    public ResponseResult collectionBehavior(@RequestBody CollectionBehaviorDto dto){
        return articleCollectionSerivce.collectionBehavior(dto);
    }
}

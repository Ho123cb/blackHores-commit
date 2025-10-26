package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.service.ArticleSearchAssociateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("文章搜索联想接口")
@RestController
@RequestMapping("/api/v1/associate")
@Slf4j
public class ArticleSearchAssociateController {
    @Resource
    private ArticleSearchAssociateService articleSearchAssociateService;

    @ApiOperation("联想搜索")
    @PostMapping("/search")
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto) {
        return articleSearchAssociateService.search(userSearchDto);
    }
}

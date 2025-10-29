package com.heima.article.controller.v1;

import com.heima.article.service.IApArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("文章基本服务接口")
@RequestMapping("/api/v1/article")
@RestController
public class ArticleHomeController {
    @Resource
    private IApArticleService iApArticleService;


    @ApiOperation("文章首页加载")
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto dto){
        ResponseResult result = iApArticleService.load(ArticleConstants.LOADTYPE_LOAD_MORE,dto);
        return result;
    }

    @ApiOperation("加载更多文章（向下滑动）")
    @PostMapping("/loadmore")
    public ResponseResult loadmore(@RequestBody ArticleHomeDto dto){
        ResponseResult result = iApArticleService.load(ArticleConstants.LOADTYPE_LOAD_MORE,dto);
        return result;
    }

    @ApiOperation("加载最新文章（向上翻）")
    @PostMapping("/loadnew")
    public ResponseResult loadnew(@RequestBody ArticleHomeDto dto){
        ResponseResult result = iApArticleService.load(ArticleConstants.LOADTYPE_LOAD_NEW,dto);
        return result;
    }

    /**
     * 自定义保存文章方法
     * @param dto
     * @return
     */
    @ApiOperation("保存文章")
    @PostMapping("/save")
    public ResponseResult customSave(@RequestBody ArticleDto dto){
        return iApArticleService.customSave(dto);
    }

    /**
     * 用于测试seata
     */
    @GetMapping("/saves")
    public ResponseResult saves(){
        ApArticle article = new ApArticle();
        article.setAuthorName("sdadsa");
        iApArticleService.save(article);
        return null;
    }

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApArticle findOne(@PathVariable("id") Long id) {
        if(id == null)
            return  null;

        return iApArticleService.getById(id);
    }

}

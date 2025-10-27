package com.heima.wemedia.controller.v1;

import com.heima.common.constants.WemediaConstants;
import com.heima.common.constants.WmNewsMessageConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.dtos.WmNewsUpOrDownDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("自媒体文章相关接口")
@RestController
@RequestMapping("/api/v1/news")
@Slf4j
public class NewsController {
    @Resource
    private WmNewsService wmNewsService;

    @ApiOperation("自媒体文章列表查询")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody WmNewsPageReqDto wmNewsPageReqDto) {
        return wmNewsService.customList(wmNewsPageReqDto);
    }

    @ApiOperation("自媒体文章保存和修改")
    @PostMapping("/submit")
    public ResponseResult submit(@RequestBody WmNewsDto dto) {
        return wmNewsService.submit(dto);
    }

    @ApiOperation("自媒体文章上下架")
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsUpOrDownDto dto) {
        return wmNewsService.downOrUp(dto);
    }

    @ApiOperation("自媒体文章分页模糊查询")
    @PostMapping("/list_vo")
    public ResponseResult listVO(@RequestBody NewsAuthDto dto) {
        return wmNewsService.listVO(dto);
    }




    @ApiOperation("自媒体文章分页模糊查询")
    @GetMapping("/one_vo/{id}")
    public ResponseResult oneVO(@PathVariable("id") Integer id) {
        return wmNewsService.oneVO(id);
    }

    @ApiOperation("自媒体文章审核失败")
    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody NewsAuthDto dto) {
        return wmNewsService.updateStatus( dto, WemediaConstants.WM_NEWS_REVIEW_FAIL);
    }

    @ApiOperation("自媒体文章审核成功")
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto) {
        return wmNewsService.updateStatus(dto , WemediaConstants.WM_NEWS_REVIEW_PASS);
    }
}

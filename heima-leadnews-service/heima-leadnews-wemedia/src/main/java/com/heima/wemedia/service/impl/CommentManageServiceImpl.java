package com.heima.wemedia.service.impl;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.CommentManageDto;
import com.heima.wemedia.service.CommentManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CommentManageServiceImpl implements CommentManageService {
    @Resource
    private ArticleOpenFeignClient articleOpenFeignClient;
    @Override
    public ResponseResult findNewsComments(CommentManageDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        //调用文章微服务查询评论
        return articleOpenFeignClient.findNewsComments(dto);
    }
}

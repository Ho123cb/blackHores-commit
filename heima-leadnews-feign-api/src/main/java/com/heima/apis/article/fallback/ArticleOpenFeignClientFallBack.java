package com.heima.apis.article.fallback;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Slf4j
public class ArticleOpenFeignClientFallBack implements ArticleOpenFeignClient {
    @Override
    public ResponseResult custoSave(@RequestBody ArticleDto dto) {
        log.error("leadnews-article服务访问异常");
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "leadnews-article服务访问异常");
    }
}

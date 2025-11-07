package com.heima.apis.article.fallback;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleFallbackFactory implements FallbackFactory<ArticleOpenFeignClient> {
    @Override
    public ArticleOpenFeignClient create(Throwable cause) {
        log.error("Feign 调用失败：{}", cause.toString(), cause);
        return new ArticleOpenFeignClient() {
            @Override
            public ResponseResult custoSave(ArticleDto dto) {
                return ResponseResult.errorResult(500, "降级：" + cause.getClass().getSimpleName());
            }

            @Override
            public ResponseResult saves() {
                return ResponseResult.errorResult(500, "降级：" + cause.getClass().getSimpleName());
            }

            @Override
            public ApArticle findOne(Long id) {
                return null;
            }

        };
//        return dto -> ResponseResult.errorResult(500, "降级：" + cause.getClass().getSimpleName());
    }

}

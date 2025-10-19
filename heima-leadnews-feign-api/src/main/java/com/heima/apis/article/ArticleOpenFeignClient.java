package com.heima.apis.article;

import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("leadnews-article")
public interface ArticleOpenFeignClient {
    /**
     * 自定义保存文章方法
     * @param dto
     * @return
     */
    @PostMapping("/api/v1/article/save")
    public ResponseResult custoSave(@RequestBody ArticleDto dto);
}

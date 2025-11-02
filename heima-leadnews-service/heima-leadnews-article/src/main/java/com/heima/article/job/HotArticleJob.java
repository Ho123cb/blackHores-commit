package com.heima.article.job;

import com.heima.article.service.ArticleHotJobService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class HotArticleJob {
    @Resource
    private ArticleHotJobService articleHotJobService;
    /**
     * 热文章定时计算：
     * 对每一个频道都进行查询出最热的30篇文章
     */
    @XxlJob("hotArticleJobHandler")
    public void shardingJobHandler(){
        articleHotJobService.hotArticle();
    }


}
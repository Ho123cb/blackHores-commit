package com.heima.wemedia.service.impl;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmNewsMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TestService {
    @Resource
    private WmNewsMapper wmNewsMapper;
    @Resource
    private ArticleOpenFeignClient articleOpenFeignClient;
    @GlobalTransactional
    public void doTest() {
        // 插入、抛异常
        log.info("开始测试新闻seata:xid={}", RootContext.getXID());
        WmNews wmNews = new WmNews();
        wmNews.setTitle("测试");
        wmNews.setReason("sdada");
        wmNewsMapper.insert(wmNews);
        log.info("插入成功seata:xid={}", RootContext.getXID());
        int i = 10 / 0 ;
        log.info("文章seata:xid={}", RootContext.getXID());
        articleOpenFeignClient.saves();
        log.info("文章保存成功seata:xid={}", RootContext.getXID());
    }
}

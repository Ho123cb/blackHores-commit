package com.heima.wemedia;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsService;
import com.heima.wemedia.service.impl.TestService;
import com.heima.wemedia.service.impl.WmNewsServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = WemediaApplication.class)
public class SeataTest {
    @Resource
    private WmNewsMapper wmNewsMapper;
    @Resource
    private ArticleOpenFeignClient articleOpenFeignClient;
    @Resource
    private TestService testService;
    //seata测试
    @Test
    public void test(){
            testService.doTest();
    }
}

package com.heima.article.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Configuration
public class XxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
    // Log initialization message
    // Create new instance of XXL-Job Spring executor
        logger.info(">>>>>>>>>>> xxl-job config init.");
    // Configure executor properties with values from application configuration
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();  // Set XXL-Job admin addresses
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);                // Set application name
        xxlJobSpringExecutor.setAppname(appname);                      // Set executor port
    // Return the configured executor instance
        xxlJobSpringExecutor.setPort(port);
        return xxlJobSpringExecutor;
    }
}
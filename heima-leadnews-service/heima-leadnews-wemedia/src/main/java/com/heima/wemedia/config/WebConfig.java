package com.heima.wemedia.config;

import com.heima.wemedia.interrupt.WmMateriaInterrupt;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WmMateriaInterrupt())
                .addPathPatterns("/**");
    }
}

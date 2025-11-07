package com.heima.comment.config;


import com.heima.comment.interrupt.CommentTokenInterrupt;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommentTokenInterrupt())
                .addPathPatterns("/**");
    }
}

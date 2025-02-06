package com.nlt.myapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 使用绝对路径或相对路径
        String path = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + path)
                .setCachePeriod(0); // 禁用缓存
    }
}
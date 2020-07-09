package com.example.convert;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
        String rootPath = System.getProperty("user.dir");
        File input = new File(rootPath);
        String filePath = input.getParent();
        String outputPath = filePath + "\\Final-input\\";
        registry.addResourceHandler("/videos/**").addResourceLocations("file:" + outputPath);
    }
}
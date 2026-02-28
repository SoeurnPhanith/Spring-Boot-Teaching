package com.example.spring_mini_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageAccessConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    //this method is នេះអនុញ្ញាតឲ្យយើង map URL to file system folder
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //URL ចាប់ផ្តើម /images/ នឹង access file នៅ folder
        registry.addResourceHandler("/uploads/**")

                //Folder path ដែល Spring នឹងបង្ហាញ
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/" + uploadDir + "/");

    }
}
package com.example.spring_mini_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImageConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Bean
    public Path uploadPath(){
        Path path = Paths.get(System.getProperty("user.dir"), uploadDir);
        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return path;
    }

}
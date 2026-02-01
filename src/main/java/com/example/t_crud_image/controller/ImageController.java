package com.example.t_crud_image.controller;

import com.example.t_crud_image.entity.ImageEntity;
import com.example.t_crud_image.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @PostMapping
    public ResponseEntity<ImageEntity> postImage(
           @ModelAttribute ImageEntity image,
           @RequestParam("images") MultipartFile file
    )throws IOException {
        return imageService.uploadImages(image, file);
    }

}

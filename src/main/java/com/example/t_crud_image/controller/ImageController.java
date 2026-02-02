package com.example.t_crud_image.controller;

import com.example.t_crud_image.entity.ImageEntity;
import com.example.t_crud_image.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ImageEntity>> showAllImages(){
        return imageService.viewAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageEntity> findImageById(@PathVariable Long id){
        return imageService.findImageById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageEntity> updateImageById(
            @ModelAttribute ImageEntity image,
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file
    ) throws IOException{
        return imageService.updateImagesById(image, id, file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeImage(@PathVariable Long id){
        return imageService.removeById(id);
    }

}

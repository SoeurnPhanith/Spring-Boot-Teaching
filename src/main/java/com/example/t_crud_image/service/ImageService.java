package com.example.t_crud_image.service;

import com.example.t_crud_image.entity.ImageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {

    ResponseEntity<ImageEntity> uploadImages(ImageEntity image, MultipartFile file) throws IOException;

}

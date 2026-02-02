package com.example.t_crud_image.service;

import com.example.t_crud_image.entity.ImageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {

    ResponseEntity<ImageEntity> uploadImages(ImageEntity image, MultipartFile file) throws IOException;

    ResponseEntity<List<ImageEntity>> viewAllImages();

    ResponseEntity<ImageEntity> findImageById(Long id);

    ResponseEntity<ImageEntity> updateImagesById(ImageEntity image, Long id, MultipartFile file) throws IOException;

    ResponseEntity<?> removeById(Long id);

}

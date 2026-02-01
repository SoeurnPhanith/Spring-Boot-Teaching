package com.example.t_crud_image.service;

import com.example.t_crud_image.entity.ImageEntity;
import com.example.t_crud_image.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Path uploadPath;

    @Override
    public ResponseEntity<ImageEntity> uploadImages(ImageEntity image, MultipartFile file) throws IOException {
        if(file.isEmpty() || file == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        //1. generate file name
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        //2.save to folder upload
        Path filePath = uploadPath.resolve(filename);
        file.transferTo(filePath.toFile());

        //3.save image to table
        String httpPath = "http://localhost:8080";
        image.setImageUrl(httpPath + "/uploads/" + filename);

        image.setTitle(image.getTitle());
        ImageEntity saved = imageRepository.save(image);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}

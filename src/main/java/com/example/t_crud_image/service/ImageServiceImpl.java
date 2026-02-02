package com.example.t_crud_image.service;

import com.example.t_crud_image.entity.ImageEntity;
import com.example.t_crud_image.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Path uploadPath;

    @Override
    public ResponseEntity<ImageEntity> uploadImages(ImageEntity image, MultipartFile file) throws IOException {
        if (file.isEmpty() || file == null) {
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

    @Override
    public ResponseEntity<List<ImageEntity>> viewAllImages() {
        //get all data from db
        List<ImageEntity> allImages = imageRepository.findAll();
        if (allImages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().body(allImages);
    }

    @Override
    public ResponseEntity<ImageEntity> findImageById(Long id) {
        //find image by id
        Optional<ImageEntity> findImage = imageRepository.findById(id);
        {
            if (!findImage.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            ImageEntity getImage = findImage.get();
            return ResponseEntity.ok().body(getImage);
        }

    }

    @Override
    public ResponseEntity<ImageEntity> updateImagesById(ImageEntity image, Long id, MultipartFile file) throws IOException {
        //find image by id
        Optional<ImageEntity> findImage = imageRepository.findById(id);
        if(!findImage.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //get image found
        ImageEntity update = findImage.get();

        if(file.isEmpty() || file == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        //1. get generate file name
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        //2.save image to root directory
        Path filePath = uploadPath.resolve(filename);
        file.transferTo(filePath.toFile());

        String httpPath =  "http://localhost:8080";
        update.setImageUrl(httpPath + "/uploads/" + filename);
        update.setTitle(image.getTitle());

        //save to db
        ImageEntity saved = imageRepository.save(update);
        return ResponseEntity.ok().body(saved);
    }

    @Override
    public ResponseEntity<?> removeById(Long id) {
        //find image by id
        Optional<ImageEntity> findImage = imageRepository.findById(id);
        if(!findImage.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //get image found
        ImageEntity getImage = findImage.get();
        imageRepository.deleteById(id);

        return ResponseEntity.ok().body("remove success");
    }

}

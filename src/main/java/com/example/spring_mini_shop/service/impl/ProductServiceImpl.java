package com.example.spring_mini_shop.service.impl;

import com.example.spring_mini_shop.dto.request_dto.ProductRequestDto;
import com.example.spring_mini_shop.dto.response_dto.ProductResponseDto;
import com.example.spring_mini_shop.entity.CategoryEntity;
import com.example.spring_mini_shop.entity.ProductEntity;
import com.example.spring_mini_shop.exception.DuplicateValueException;
import com.example.spring_mini_shop.exception.ResourceNotFoundException;
import com.example.spring_mini_shop.repo.CategoryRepository;
import com.example.spring_mini_shop.repo.ProductRepository;
import com.example.spring_mini_shop.service.ProductService;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Path uploadPath;


    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(ProductRequestDto dto, MultipartFile file) throws IOException {
        //check exists product
        boolean existsProduct = productRepository.existsByName(dto.getName());
        if(existsProduct){
           throw new DuplicateValueException("Product name already exists");
        }
        //find category
        CategoryEntity findById = categoryRepository.findById(dto.getCategory()).
                orElseThrow(()->new ResourceNotFoundException("category not found"));

        //map data from request dto -> entity
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setCategory(findById);
        entity.setPrice(dto.getPrice());
        //get image
        if(file!=null){
            String fileName = UUID.randomUUID()+ "_" + file.getOriginalFilename();

            //set image to root directory
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            String http = "http://localhost:8080";
            entity.setImageUrl(http + "/uploads/" + fileName );
        }

        //save to db
        ProductEntity saved = productRepository.save(entity);

        //map data from entity -> response DTO
        ProductResponseDto response = new ProductResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCategory(saved.getCategory().getName());
        response.setPrice(saved.getPrice());
        response.setImageUrl(saved.getImageUrl());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true, "create new resource", response
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> allProducts() {

        List<ProductResponseDto> dtoList = productRepository.findAll()
                .stream()
                .map(p -> new ProductResponseDto(
                        p.getId(),
                        p.getName(),
                        p.getCategory().getName(),
                        p.getPrice(),
                        p.getImageUrl(),
                        p.getUpdatedAt(),
                        p.getCreatedAt()
                ))
                .toList();

        if (dtoList.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        return ResponseEntity.ok(
                new ApiResponse<>(true, "success", dtoList)
        );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductById(Long id) {
        ProductResponseDto dto = productRepository.findById(id)
                .map(p->new ProductResponseDto(
                        p.getId(),
                        p.getName(),
                        p.getCategory().getName(),
                        p.getPrice(),
                        p.getImageUrl(),
                        p.getUpdatedAt(),
                        p.getCreatedAt()
                )).orElseThrow(()->new ResourceNotFoundException("product not found"));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"success", dto));
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(ProductRequestDto dto, MultipartFile file, Long id) throws IOException {

        ProductEntity find = productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("product not found!"));

        boolean existsProduct = productRepository.existsByName(dto.getName());
        if(existsProduct){
            throw new DuplicateValueException("Product name already exists");
        }

        //find category
        CategoryEntity findById = categoryRepository.findById(dto.getCategory()).
                orElseThrow(()->new ResourceNotFoundException("category not found"));

        // map data from dto -> entity
        find.setName(dto.getName());
        find.setCategory(findById);
        find.setPrice(dto.getPrice());
        if(file!=null){
            String fileName = UUID.randomUUID()+ "_" + file.getOriginalFilename();

            //set image to root directory
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            String http = "http://localhost:8080";
            find.setImageUrl(http + "/uploads/" + fileName );
        }
        //save to db
        ProductEntity saved = productRepository.save(find);

        //map data entity -> response DTO
        ProductResponseDto response = new ProductResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCategory(saved.getCategory().getName());
        response.setPrice(saved.getPrice());
        response.setImageUrl(saved.getImageUrl());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"update success", response));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteProducts(Long id) {
        return null;
    }
}
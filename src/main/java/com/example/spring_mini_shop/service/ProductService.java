package com.example.spring_mini_shop.service;

import com.example.spring_mini_shop.dto.request_dto.ProductRequestDto;
import com.example.spring_mini_shop.dto.response_dto.ProductResponseDto;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ProductService {

    ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(
            ProductRequestDto dto , MultipartFile file
    ) throws IOException;

}

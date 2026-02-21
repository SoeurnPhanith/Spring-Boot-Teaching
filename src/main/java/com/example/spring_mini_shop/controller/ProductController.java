package com.example.spring_mini_shop.controller;

import com.example.spring_mini_shop.dto.request_dto.ProductRequestDto;
import com.example.spring_mini_shop.dto.response_dto.ProductResponseDto;
import com.example.spring_mini_shop.service.impl.ProductServiceImpl;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/mini-shop/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(
            @ModelAttribute ProductRequestDto dto,
            @RequestParam("images") MultipartFile file
    ) throws IOException{
        return productService.addProduct(dto, file);
    }

}

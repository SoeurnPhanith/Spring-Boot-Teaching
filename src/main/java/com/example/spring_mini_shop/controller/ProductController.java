package com.example.spring_mini_shop.controller;

import com.example.spring_mini_shop.dto.request_dto.ProductRequestDto;
import com.example.spring_mini_shop.dto.response_dto.ProductResponseDto;
import com.example.spring_mini_shop.service.impl.ProductServiceImpl;
import com.example.spring_mini_shop.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mini-shop/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(
            @Valid @ModelAttribute ProductRequestDto dto,
            @Valid @RequestParam("images") MultipartFile file
    ) throws IOException{
        return productService.addProduct(dto, file);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> allProducts(){
        return productService.allProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductById(
            @Valid @PathVariable Long id
    ){
        return productService.findProductById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProducts(
            @Valid @ModelAttribute ProductRequestDto dto,
            @Valid @Param("images") MultipartFile file,
            @Valid @PathVariable Long id
    )throws IOException{
        return productService.updateProduct(dto, file, id);
    }

}
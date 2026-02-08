package com.example.spring_mini_shop.controller;

import com.example.spring_mini_shop.dto.request_dto.CategoryRequestDto;
import com.example.spring_mini_shop.dto.response_dto.CategoryResponseDto;
import com.example.spring_mini_shop.service.impl.CategoryServiceImpl;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mini-shop/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(@RequestBody CategoryRequestDto category){
        return categoryService.addCategory(category);
    }

}

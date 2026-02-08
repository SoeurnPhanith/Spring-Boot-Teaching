package com.example.spring_mini_shop.service;

import com.example.spring_mini_shop.dto.request_dto.CategoryRequestDto;
import com.example.spring_mini_shop.dto.response_dto.CategoryResponseDto;
import com.example.spring_mini_shop.entity.CategoryEntity;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(CategoryRequestDto category);
    ResponseEntity<ApiResponse<List<CategoryResponseDto>>> viewAllCategory();
    ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryById(Long id);
    ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(CategoryRequestDto category, Long id);
    ResponseEntity<ApiResponse<String>> deleteCategory(Long id);

}

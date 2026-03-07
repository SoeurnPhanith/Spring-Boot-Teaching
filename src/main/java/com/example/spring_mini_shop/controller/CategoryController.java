package com.example.spring_mini_shop.controller;

import com.example.spring_mini_shop.dto.request_dto.CategoryRequestDto;
import com.example.spring_mini_shop.dto.response_dto.CategoryResponseDto;
import com.example.spring_mini_shop.service.impl.CategoryServiceImpl;
import com.example.spring_mini_shop.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> allCategory(){
        return categoryService.viewAllCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryById(
            @Valid @PathVariable Long id
    ){
        return categoryService.findCategoryById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(
            @Valid @RequestBody CategoryRequestDto dto,
            @Valid @PathVariable Long id
    ){
        return categoryService.updateCategory(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(
            @Valid @PathVariable Long id
    ){
        return categoryService.deleteCategory(id);
    }

}

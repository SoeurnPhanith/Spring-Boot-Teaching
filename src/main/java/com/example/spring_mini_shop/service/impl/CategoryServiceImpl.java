package com.example.spring_mini_shop.service.impl;

import com.example.spring_mini_shop.dto.request_dto.CategoryRequestDto;
import com.example.spring_mini_shop.dto.response_dto.CategoryResponseDto;
import com.example.spring_mini_shop.entity.CategoryEntity;
import com.example.spring_mini_shop.repo.CategoryRepository;
import com.example.spring_mini_shop.service.CategoryService;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(CategoryRequestDto category) {
        //map data from request dto -> entity
        CategoryEntity entity = new CategoryEntity();
        entity.setName(category.getName());

        //save entity to db
        CategoryEntity saved = categoryRepository.save(entity);

        //map data from entity -->> response dto
        CategoryResponseDto response = new CategoryResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,"add category success", response
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> viewAllCategory() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(CategoryRequestDto category, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteCategory(Long id) {
        return null;
    }
}

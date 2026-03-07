package com.example.spring_mini_shop.service.impl;

import com.example.spring_mini_shop.dto.request_dto.CategoryRequestDto;
import com.example.spring_mini_shop.dto.response_dto.CategoryResponseDto;
import com.example.spring_mini_shop.entity.CategoryEntity;
import com.example.spring_mini_shop.exception.DuplicateValueException;
import com.example.spring_mini_shop.exception.ResourceNotFoundException;
import com.example.spring_mini_shop.repo.CategoryRepository;
import com.example.spring_mini_shop.service.CategoryService;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<CategoryResponseDto>> addCategory(CategoryRequestDto category) {

        boolean exists = categoryRepository.existsByName(category.getName());
        if(exists){
            throw new DuplicateValueException("category name already exists");
        }
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

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> viewAllCategory() {

        List<CategoryResponseDto> dtoList = categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponseDto(c.getId(), c.getName()))
                .toList();
        if (dtoList.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dtoList));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<CategoryResponseDto>> findCategoryById(Long id) {

        CategoryResponseDto dto = categoryRepository.findById(id)
                .map(entity -> new CategoryResponseDto(entity.getId(), entity.getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dto));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(CategoryRequestDto category, Long id) {

        CategoryEntity find = categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("category not found!"));

        //check exist when update
        boolean exists = categoryRepository.existsByName(category.getName());
        if(exists){
            throw new DuplicateValueException("category already exists can't not update!");
        }

        //dto -> entity
        CategoryEntity update = new CategoryEntity();
        update.setName(category.getName());
        categoryRepository.save(update);

        //entity -> dto
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setName(update.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"update category success", dto));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<String>> deleteCategory(Long id) {

        CategoryEntity category = categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("category not found!"));

        categoryRepository.delete(category);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"delete success", "done!"));
    }
}

package com.example.spring_mini_shop.dto.request_dto;

import com.example.spring_mini_shop.entity.CategoryEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    private String name;
    private BigDecimal price;
    private Long category;
    private String imageUrl;

}

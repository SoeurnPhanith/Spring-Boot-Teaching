package com.example.spring_mini_shop.dto.response_dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponseDto(Long id, String name, String name1, BigDecimal price, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.category = name1;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
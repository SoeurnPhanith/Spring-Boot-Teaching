package com.example.spring_mini_shop.dto.response_dto;

import com.example.spring_mini_shop.entity.CategoryEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponseDto {

    private Long id;
    private String name;
    private CategoryEntity category;
    private BigDecimal price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

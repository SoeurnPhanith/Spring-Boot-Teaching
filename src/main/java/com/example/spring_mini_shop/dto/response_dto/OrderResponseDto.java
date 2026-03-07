package com.example.spring_mini_shop.dto.response_dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {

    private Long id;
    private String username;       // maps from OrdersEntity.user.username
    private String productName;    // maps from OrdersEntity.product.name
    private Long qty;
    private LocalDateTime orderedAt;
}
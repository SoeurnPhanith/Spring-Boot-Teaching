package com.example.spring_mini_shop.dto.request_dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDto {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "productId is required")
    private Long productId;

    @NotNull(message = "qty is required")
    private Long qty;
}
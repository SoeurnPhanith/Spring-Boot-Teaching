package com.example.spring_mini_shop.service;

import com.example.spring_mini_shop.dto.request_dto.OrderRequestDto;
import com.example.spring_mini_shop.dto.response_dto.OrderResponseDto;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    ResponseEntity<ApiResponse<OrderResponseDto>> addOrder(OrderRequestDto dto);

    ResponseEntity<ApiResponse<OrderResponseDto>> findOrderById(Long id);

    ResponseEntity<ApiResponse<List<OrderResponseDto>>> viewAllOrders();

    ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(OrderRequestDto dto, Long id);

    ResponseEntity<ApiResponse<String>> deleteOrder(Long id);
}
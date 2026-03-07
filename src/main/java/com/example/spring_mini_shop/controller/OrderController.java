package com.example.spring_mini_shop.controller;

import com.example.spring_mini_shop.dto.request_dto.OrderRequestDto;
import com.example.spring_mini_shop.dto.response_dto.OrderResponseDto;
import com.example.spring_mini_shop.service.OrderService;
import com.example.spring_mini_shop.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mini-shop/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> addOrder(@Valid @RequestBody OrderRequestDto dto) {
        return orderService.addOrder(dto);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> viewAllOrders() {
        return orderService.viewAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> findOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(@Valid @RequestBody OrderRequestDto dto, @PathVariable Long id) {
        return orderService.updateOrder(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}
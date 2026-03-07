package com.example.spring_mini_shop.service.impl;

import com.example.spring_mini_shop.dto.request_dto.OrderRequestDto;
import com.example.spring_mini_shop.dto.response_dto.OrderResponseDto;
import com.example.spring_mini_shop.entity.OrdersEntity;
import com.example.spring_mini_shop.entity.ProductEntity;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.exception.ResourceNotFoundException;
import com.example.spring_mini_shop.repo.OrderRepository;
import com.example.spring_mini_shop.repo.ProductRepository;
import com.example.spring_mini_shop.repo.UserRepository;
import com.example.spring_mini_shop.service.OrderService;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<OrderResponseDto>> addOrder(OrderRequestDto dto) {

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        OrdersEntity order = new OrdersEntity();
        order.setUser(user);
        order.setProduct(product);
        order.setQty(dto.getQty());

        OrdersEntity saved = orderRepository.save(order);

        OrderResponseDto response = new OrderResponseDto();
        response.setId(saved.getId());
        response.setUsername(saved.getUser().getUsername());
        response.setProductName(saved.getProduct().getName());
        response.setQty(saved.getQty());
        response.setOrderedAt(saved.getOrderedAt());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "add order success", response));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> viewAllOrders() {
        List<OrderResponseDto> dtoList = orderRepository.findAll()
                .stream()
                .map(o -> {
                    OrderResponseDto dto = new OrderResponseDto();
                    dto.setId(o.getId());
                    dto.setUsername(o.getUser().getUsername());
                    dto.setProductName(o.getProduct().getName());
                    dto.setQty(o.getQty());
                    dto.setOrderedAt(o.getOrderedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        if (dtoList.isEmpty()) {
            throw new ResourceNotFoundException("Orders not found");
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dtoList));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<OrderResponseDto>> findOrderById(Long id) {
        OrdersEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setUsername(order.getUser().getUsername());
        dto.setProductName(order.getProduct().getName());
        dto.setQty(order.getQty());
        dto.setOrderedAt(order.getOrderedAt());

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dto));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(OrderRequestDto dto, Long id) {

        OrdersEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        order.setUser(user);
        order.setProduct(product);
        order.setQty(dto.getQty());

        OrdersEntity updated = orderRepository.save(order);

        OrderResponseDto response = new OrderResponseDto();
        response.setId(updated.getId());
        response.setUsername(updated.getUser().getUsername());
        response.setProductName(updated.getProduct().getName());
        response.setQty(updated.getQty());
        response.setOrderedAt(updated.getOrderedAt());

        return ResponseEntity.ok(new ApiResponse<>(true, "update order success", response));
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<String>> deleteOrder(Long id) {
        OrdersEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        orderRepository.delete(order);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "delete success", "done!"));
    }
}
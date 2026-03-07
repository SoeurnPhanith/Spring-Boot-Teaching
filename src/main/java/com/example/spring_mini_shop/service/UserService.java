package com.example.spring_mini_shop.service;

import com.example.spring_mini_shop.dto.request_dto.UserRequestDto;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<ApiResponse<?>> register(UserRequestDto user);

}

package com.example.spring_mini_shop.service;

import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<ApiResponse<UserEntity>> register(UserEntity user);

}

package com.example.spring_mini_shop.controller;

import com.example.spring_mini_shop.dto.request_dto.UserRequestDto;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.service.impl.UserServiceImpl;
import com.example.spring_mini_shop.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mini-shop/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
            @Valid @RequestBody UserRequestDto user
    ){
       return userService.register(user);
    }


}


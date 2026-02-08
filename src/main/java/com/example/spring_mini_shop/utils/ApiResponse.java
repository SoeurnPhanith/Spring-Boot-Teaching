package com.example.spring_mini_shop.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse <T>{

    private boolean success;
    private String msg;
    private T data;


}

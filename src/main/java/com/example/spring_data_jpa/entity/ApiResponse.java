package com.example.spring_data_jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse <T>{

    private String message;

    private Integer statusCode;

    private T data;

}

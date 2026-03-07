package com.example.spring_mini_shop.dto.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "username must be character")
    private String username;

    @NotBlank(message = "email is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.(com|edu|gov|org)\\.kh$",
            message = "email must be valid (.com.kh, .edu.kh, .gov.kh, .org.kh)"
    )
    private String email;

    @NotBlank(message = "password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
    )
    private String password;

}

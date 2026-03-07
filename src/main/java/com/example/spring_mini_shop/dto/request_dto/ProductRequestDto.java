package com.example.spring_mini_shop.dto.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotNull(message = "name is required")
    @NotBlank(message = "Field name is required | not blank")
  //  @Pattern(regexp = "^[a-zA-Z ]*$", message = "Name must contain only characters")
    private String name;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @NotNull(message = "category is required")
    private Long category;

    private MultipartFile images;

}
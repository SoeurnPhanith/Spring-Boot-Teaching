package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.entity.ApiResponse;
import com.example.spring_data_jpa.entity.Employee;
import com.example.spring_data_jpa.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> showAllEmp(){
        return service.showAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee){
        return service.createEmployee(employee);
    }
}

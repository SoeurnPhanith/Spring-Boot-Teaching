package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.entity.ApiResponse;
import com.example.spring_data_jpa.entity.Employee;
import com.example.spring_data_jpa.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Employee>>> findEmployeeById(@PathVariable Long id){
        return service.findEmployeeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
           @RequestBody Employee employee ,
           @PathVariable Long id
    ){
        return service.updateEmployee(employee, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable Long id){
        return service.removeEmployee(id);
    }
}

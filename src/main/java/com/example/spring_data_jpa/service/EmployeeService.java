package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.entity.ApiResponse;
import com.example.spring_data_jpa.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    ResponseEntity<ApiResponse<Employee>> createEmployee(Employee emp);

    ResponseEntity<ApiResponse<List<Employee>>> showAll();


}

package com.example.spring_data_jpa.service.impl;

import com.example.spring_data_jpa.entity.ApiResponse;
import com.example.spring_data_jpa.entity.Employee;
import com.example.spring_data_jpa.repo.EmployeeRepository;
import com.example.spring_data_jpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    //inject data from repo
    @Autowired
    private EmployeeRepository empRepo;

    @Override
    public ResponseEntity<ApiResponse<Employee>> createEmployee(Employee emp) {
        //1.check duplicate data
        boolean exists = empRepo.existsByEmail(emp.getEmail());
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(
                    "this email already exists", 409, null
            ));
        }

        Employee addEmp = empRepo.save(emp);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                "created new resource success", 201, addEmp
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<List<Employee>>> showAll() {
           List<Employee> getAll = empRepo.findAll();
           if(getAll.isEmpty()){
               return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
           }
            return ResponseEntity.ok().body(
                    new ApiResponse<>(
                            "all employee",
                            200,
                            getAll
                    )
            );
    }
}

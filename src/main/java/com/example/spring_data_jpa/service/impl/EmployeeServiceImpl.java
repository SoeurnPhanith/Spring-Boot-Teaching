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
import java.util.Optional;

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

    @Override
    public ResponseEntity<ApiResponse<Optional<Employee>>> findEmployeeById(Long id) {
        //1. get data from db by id
        Optional<Employee> find = empRepo.findById(id);
        if(!find.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "get employee id " + id + " not found ",
                    404, null
            ));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(
                "success", 200, find
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(Employee emp, Long id) {
        //1.find employee by id
        Optional<Employee> findEmp = empRepo.findById(id);
        if(!findEmp.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "get employee id " + id + " not found ",
                    404, null
            ));
        }

        //2. get data found to update
        Employee update = findEmp.get();
        update.setName(emp.getName());
        update.setEmail(emp.getEmail());
        update.setGender(emp.getGender());
        update.setPosition(emp.getPosition());
        update.setSalary(emp.getSalary());

        //3. Save data updated to db
        Employee saved = empRepo.save(update);
        return ResponseEntity.ok().body(new ApiResponse<>(
                "update resource success",
                200, update
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<String>> removeEmployee(Long id) {
        //1.find employee by id
        Optional<Employee> findEmp = empRepo.findById(id);
        if(!findEmp.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "get employee id " + id + " not found ",
                    404, null
            ));
        }

        empRepo.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse<>(
                "delete success",
                200, "done"
        ));
    }
}

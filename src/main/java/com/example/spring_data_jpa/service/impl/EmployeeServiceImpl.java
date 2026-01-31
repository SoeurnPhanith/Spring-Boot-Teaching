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

        //2. validate on column name
        if(emp.getName().isBlank() || emp.getName().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "name is required", 400, null
            ));
        }
        if(!emp.getName().matches("^[a-zA-Z ]+$")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "name must be only character", 400, null
            ));
        }
        if(emp.getName().length() < 5){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "name should be more than 5 character", 400, null
            ));
        }

        //3. validate on column gender
        if(emp.getGender().isEmpty() || emp.getGender().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "gender is required", 400, null
            ));
        }
        if(
                !(emp.getGender().equalsIgnoreCase("male") ||
                emp.getGender().equalsIgnoreCase("female"))
        ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "gender is only male | female", 400, null
            ));
        }

        //4. validate on email
        if(emp.getEmail().isBlank() || emp.getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "email is required", 400, null
            ));
        }
        if(emp.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "Invalid email ! so email must be email", 400, null

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

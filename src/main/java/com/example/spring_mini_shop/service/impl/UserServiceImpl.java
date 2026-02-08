package com.example.spring_mini_shop.service.impl;

import com.example.spring_mini_shop.entity.RoleEntity;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.repo.RoleRepository;
import com.example.spring_mini_shop.repo.UserRepository;
import com.example.spring_mini_shop.service.UserService;
import com.example.spring_mini_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepo;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepo) {
        this.userRepository = userRepository;
        this.roleRepo = roleRepo;
    }

    @Override
    public ResponseEntity<ApiResponse<UserEntity>> register(UserEntity user) {
        //check exists user
        boolean existsUser = userRepository.existsByEmail(user.getEmail());
        if(existsUser){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(
                    false,"user already register", null
            ));
        }

        //validate on username
        if(user.getUsername().isEmpty() || user.getUsername().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,"username is required",null
            ));
        }
        if(!user.getUsername().matches("^[a-zA-Z ]+$")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,"username must be character",null
            ));
        }

        //validate on email
        if(user.getEmail().isEmpty() || user.getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,"email is required",null
            ));
        }boolean existsUser = userRepository.existsByEmail(user.getEmail());
        if(existsUser){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(
                    false,"user already register", null
            ));
        }

        //validate on userna
        if(!user.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.(com|edu|gov|org)\\.kh$\n")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false,"email must be email", null)
            );
        }

        //validate on password
        if(user.getPassword().isEmpty() || user.getPassword().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,"password is required",null
            ));
        }
        if (!user.getPassword().matches(
                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$"
        )) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            false,
                            "Password must be at least 8 characters and include 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.",
                            null
                    ));
        }

        //get role from role table(db)
        Optional<RoleEntity> findRole = roleRepo.findByName("ROLE_USER");
        if(!findRole.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false,"role user not found", null)
            );
        }
        //get data to normal object
        RoleEntity getRole = findRole.get();

        //assign default role
        user.setRole(getRole);
        UserEntity save = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,"user register successful",save
        ));
    }
}

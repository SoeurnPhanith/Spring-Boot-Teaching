package com.example.spring_mini_shop.service.impl;

import com.example.spring_mini_shop.dto.request_dto.UserRequestDto;
import com.example.spring_mini_shop.dto.response_dto.UserResponseDto;
import com.example.spring_mini_shop.entity.RoleEntity;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.exception.ResourceNotFoundException;
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
    public ResponseEntity<ApiResponse<?>> register(UserRequestDto user) {
        //check exists user
        boolean existsUser = userRepository.existsByEmail(user.getEmail());
        if(existsUser){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(
                    false,"user already register", null
            ));
        }
        //get role from role table(db)
        RoleEntity findRole = roleRepo.findByName("ROLE_USER").orElseThrow(
                ()-> new ResourceNotFoundException("Role not found!")
        );
        //map data from dto->entity
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRole(findRole);
        UserEntity save = userRepository.save(entity);

        //map from entity -> dto
        UserResponseDto dto = new UserResponseDto();
        dto.setId(save.getId());
        dto.setUsername(save.getUsername());
        dto.setEmail(save.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,"user register successful",dto
        ));
    }
}

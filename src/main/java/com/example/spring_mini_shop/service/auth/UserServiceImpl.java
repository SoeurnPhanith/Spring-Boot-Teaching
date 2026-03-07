package com.example.spring_mini_shop.service.auth;

import com.example.spring_mini_shop.dto.request_dto.UserRequestDto;
import com.example.spring_mini_shop.dto.response_dto.UserResponseDto;
import com.example.spring_mini_shop.entity.RoleEntity;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.exception.ResourceNotFoundException;
import com.example.spring_mini_shop.repo.RoleRepository;
import com.example.spring_mini_shop.repo.UserRepository;
import com.example.spring_mini_shop.service.UserService;
import com.example.spring_mini_shop.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HttpServletRequest request;
    private final UserRepository userRepository;
    private final RoleRepository roleRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepo,
            AuthenticationManager authenticationManager, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> register(UserRequestDto user) {
        // check exists user
        boolean existsUser = userRepository.existsByEmail(user.getEmail());
        if (existsUser) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(
                    false, "user already register", null));
        }
        // get role from role table(db)
        RoleEntity findRole = roleRepo.findByName("ROLE_USER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found!"));
        // map data from dto->entity
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(encoder.encode(user.getPassword()));
        entity.setRole(findRole);
        UserEntity save = userRepository.save(entity);

        // map from entity -> dto
        UserResponseDto dto = new UserResponseDto();
        dto.setId(save.getId());
        dto.setUsername(save.getUsername());
        dto.setEmail(save.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true, "user register successful", dto));
    }

    public String login(String email, String password) {
        // is a object to store user credentail for submit to authenticationManager
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            // validate login success or not
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                // make spring remember this user to access every end-point follow his role
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.getSession(true).setAttribute(
                        "SPRING_SECURITY_CONTEXT",
                        SecurityContextHolder.getContext());

                return "Login Success";
            } else {
                return "Something wrong on email or password";
            }

        } catch (BadCredentialsException ex) {
            return "Something wrong on email or password";
        }
    }
}

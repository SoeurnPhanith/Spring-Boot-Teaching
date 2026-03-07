package com.example.spring_mini_shop.service.auth;

import com.example.spring_mini_shop.entity.RoleEntity;
import com.example.spring_mini_shop.entity.UserEntity;
import com.example.spring_mini_shop.exception.ResourceNotFoundException;
import com.example.spring_mini_shop.repo.RoleRepository;
import com.example.spring_mini_shop.repo.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomerUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("user not found!"));
        RoleEntity role = roleRepository.findByName("ROLE_USER").orElseThrow(
                ()->new ResourceNotFoundException("role not found"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(role.getName())
                .build();
    }
}

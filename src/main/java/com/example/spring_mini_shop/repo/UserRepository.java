package com.example.spring_mini_shop.repo;

import com.example.spring_mini_shop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

}

package com.example.spring_mini_shop.repo;

import com.example.spring_mini_shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByName(String name);

}

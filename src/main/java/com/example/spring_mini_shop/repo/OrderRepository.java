package com.example.spring_mini_shop.repo;

import com.example.spring_mini_shop.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity,Long> {
}

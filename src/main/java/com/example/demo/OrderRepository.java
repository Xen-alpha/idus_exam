package com.example.demo;

import com.example.demo.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    public OrderEntity findFirstByOrderDateOrderByOrderDateDesc(Date orderDate);
}

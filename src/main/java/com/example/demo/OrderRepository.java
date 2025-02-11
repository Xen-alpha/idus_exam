package com.example.demo;

import com.example.demo.model.OrderEntity;
import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    public Optional<OrderEntity> findFirstByUserOrderByOrderDateDesc(UserEntity user);
    public List<OrderEntity> findAllByUser(UserEntity user);
}

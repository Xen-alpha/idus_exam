package com.example.demo;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUsernameAndEmail(String username, String email);
}

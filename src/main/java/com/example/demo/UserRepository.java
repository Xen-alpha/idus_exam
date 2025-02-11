package com.example.demo;

import com.example.demo.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public List<UserEntity> findAllByUsernameAndEmail(String username, String email, Pageable pageable);
    public List<UserEntity> findAllByUsername(String username, Pageable pageable);
    public List<UserEntity> findAllByEmail(String email, Pageable pageable);
}

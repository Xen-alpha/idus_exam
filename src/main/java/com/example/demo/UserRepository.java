package com.example.demo;

import com.example.demo.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByNickname(String nickname);
    public List<UserEntity> findAllByNicknameAndEmail(String nickname, String email, Pageable pageable);
    public List<UserEntity> findAllByNickname(String nickname, Pageable pageable);
    public List<UserEntity> findAllByEmail(String email, Pageable pageable);
    Optional<UserEntity> findByIdx(Long idx);
}

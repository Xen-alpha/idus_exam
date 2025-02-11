package com.example.demo;

import com.example.demo.model.UserEntity;
import com.example.demo.model.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public void signup(SignupRequest signupRequest) {
        // TODO: 빌더 패턴?
        UserEntity user = new UserEntity();
        user.setName(signupRequest.getName());
        user.setNickname(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setPhone(signupRequest.getPhone());
        user.setEmail(signupRequest.getEmail());
        user.setGender(signupRequest.getGender());
        user.setOrders(new ArrayList<>());
        userRepository.save(user);
    }

    public UserDetailDto getUserDetail(Long idx) {
        UserDetailDto result = new UserDetailDto();
        // TODO: Branching UserRepository.findBy...
        Optional<UserEntity> user = userRepository.findById(idx);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            result.setIdx(userEntity.getIdx());
            result.setName(userEntity.getNickname());
            result.setPhone(userEntity.getPhone());
            result.setEmail(userEntity.getEmail());
            result.setGender(userEntity.getGender());
        }
        return result;
    }

    public OrderDto getUserOrder(Long idx) {
        OrderDto result = new OrderDto();
        // TODO: orderRepository.findAllBy...

        return result;
    }

    public UserListDto getUserList(String email, String username, Integer page) {
        UserListDto result = new UserListDto();
        // TODO: userRepository.findAll(...).getContents()

        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}

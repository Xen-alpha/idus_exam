package com.example.demo;

import com.example.demo.model.UserEntity;
import com.example.demo.model.user.SignupRequest;
import com.example.demo.model.user.UserDetailResponse;
import com.example.demo.model.user.UserListResponse;
import com.example.demo.model.user.UserOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public void signup(SignupRequest signupRequest) {
        // TODO: UserRepository Save
    }

    public UserDetailResponse getUserDetail(Long idx) {
        UserDetailResponse response = new UserDetailResponse();
        // TODO: Branching UserRepository.findBy...
        return response;
    }

    public UserOrderResponse getUserOrder(Long idx) {
        UserOrderResponse response = new UserOrderResponse();
        // TODO: orderRepository.findAllBy...

        return response;
    }

    public UserListResponse getUserList(String email, String username, Integer page) {
        UserListResponse response = new UserListResponse();
        // TODO: userRepository.findAll(...).getContents()

        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}

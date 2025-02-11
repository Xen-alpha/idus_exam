package com.example.demo;

import com.example.demo.model.OrderEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public UserDetailDto getUserDetail(Long idx) {
        UserDetailDto result = new UserDetailDto();
        Optional<UserEntity> user = userRepository.findById(idx);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            result.setIdx(userEntity.getIdx());
            result.setName(userEntity.getNickname());
            result.setPhone(userEntity.getPhone());
            result.setEmail(userEntity.getEmail());
            result.setGender(userEntity.getGender());
            return result;
        }
        return null;
    }

    public List<OrderDto> getUserOrder(Long idx) {
        List<OrderDto> result = new ArrayList<>();
        Optional<UserEntity> user = userRepository.findById(idx);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            List<OrderEntity> orders = orderRepository.findAllByUser(userEntity);
            for (OrderEntity orderEntity : orders) {
                OrderDto orderDto = new OrderDto();
                orderDto.setOrderId(orderEntity.getOrderId());
                orderDto.setProductName(orderEntity.getProductName());
                orderDto.setDatetime(orderEntity.getOrderDate());
                result.add(orderDto);
            }
        }
        return result;
    }

    public List<UserListDto> getUserList(String email, String username, Integer page) {
        List<UserListDto> result = new ArrayList<>();
        List<UserEntity> users;
        // 이게 별로 좋은 방법은 아니지만... null인 것이라면?
        if (page == null) page = 0;
        if (email == null) email = "";
        if (username == null) username = "";
        if (username.isEmpty() && email.isEmpty()) {
            users = userRepository.findAll(PageRequest.of(page, 10)).getContent();
        } else if (username.isEmpty()) {
            users = userRepository.findAllByEmail(email, PageRequest.of(page, 10));
        } else if (email.isEmpty()) {
            users = userRepository.findAllByNickname(username, PageRequest.of(page, 10));
        } else {
            users = userRepository.findAllByNicknameAndEmail(email, username, PageRequest.of(page, 10));
        }
        for (UserEntity userEntity : users) {
            UserListDto userListDto = new UserListDto();
            UserDetailDto userDetailDto = getUserDetail(userEntity.getIdx());
            userListDto.setUserDetail(userDetailDto);
            Optional<OrderEntity> lastorder = orderRepository.findFirstByUserOrderByOrderDateDesc(userEntity);
            if (lastorder.isPresent()) {
                OrderEntity orderEntity = lastorder.get();
                OrderDto orderDto = new OrderDto();
                orderDto.setOrderId(orderEntity.getOrderId());
                orderDto.setProductName(orderEntity.getProductName());
                orderDto.setDatetime(orderEntity.getOrderDate());
                userListDto.setLastOrder(orderDto);
            } else {
                userListDto.setLastOrder(null);
            }
            result.add(userListDto);
        }
        
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByNickname(username);
        return user.orElse(null);
    }
}

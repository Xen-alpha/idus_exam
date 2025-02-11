package com.example.demo;

import com.example.demo.model.common.BaseResponse;
import com.example.demo.model.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signup(@RequestBody SignupRequest request) {
        System.out.println("Signup request detected");
        BaseResponse response = new BaseResponse();
        userService.signup(request);
        response.setSuccess();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{idx}")
    public ResponseEntity<UserDetailResponse> getUserInfo(@PathVariable Long idx) {
        UserDetailResponse response = new UserDetailResponse();
        UserDetailDto dto = userService.getUserDetail(idx);
        if (dto == null) {
            response.setCode(72000);
            response.setMessage("Not found");
            response.setDetail(null);
            return ResponseEntity.notFound().build();
        }
        response.setSuccess();
        response.setDetail(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/order/{idx}")
    public ResponseEntity<UserOrderResponse> getUserOrder(@PathVariable Long idx) {
        UserOrderResponse response = new UserOrderResponse();
        List<OrderDto> dtos = userService.getUserOrder(idx);
        if (dtos.isEmpty()) {
            response.setEmpty();
        } else {
            response.setSuccess();
        }
        response.setOrders(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<UserListResponse> getUserList(String name, String email, Integer page) {
        UserListResponse response = new UserListResponse();
        List<UserListDto> dtos = userService.getUserList(email, name, page);
        if (dtos.isEmpty()) {
            response.setEmpty();
        } else {
            response.setSuccess();
        }
        response.setList(dtos);
        return ResponseEntity.ok(response);
    }
}

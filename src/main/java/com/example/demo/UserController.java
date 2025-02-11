package com.example.demo;

import com.example.demo.model.common.BaseResponse;
import com.example.demo.model.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signup(@RequestBody SignupRequest request) {
        BaseResponse response = new BaseResponse();
        // TODO: 유저서비스 메서드 호출
        response.setCode(10000);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{idx}")
    public ResponseEntity<UserDetailResponse> getUserInfo(@PathVariable Long idx) {
        UserDetailResponse response = new UserDetailResponse();
        response.setCode(20000);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/order/{idx}")
    public ResponseEntity<UserOrderResponse> getUserOrder(@PathVariable Long idx) {
        UserOrderResponse response = new UserOrderResponse();
        response.setCode(20001);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<UserListResponse> getUserList() {
        UserListResponse response = new UserListResponse();
        response.setCode(30000);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
}

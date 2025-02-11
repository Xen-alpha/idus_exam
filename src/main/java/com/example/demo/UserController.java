package com.example.demo;

import com.example.demo.model.common.BaseResponse;
import com.example.demo.model.user.LoginRequest;
import com.example.demo.model.user.SignupRequest;
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
    @GetMapping("/info")
    public ResponseEntity<BaseResponse> getUserInfo() {
        BaseResponse response = new BaseResponse();
        response.setCode(20000);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/order")
    public ResponseEntity<BaseResponse> getUserOrder() {
        BaseResponse response = new BaseResponse();
        response.setCode(20001);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getUserList() {
        BaseResponse response = new BaseResponse();
        response.setCode(30000);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
}

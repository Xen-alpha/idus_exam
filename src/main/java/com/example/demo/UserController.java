package com.example.demo;

import com.example.demo.model.common.BaseResponse;
import com.example.demo.model.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        userService.signup(request);
        response.setCode(10000);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/{idx}")
    public ResponseEntity<UserDetailResponse> getUserInfo(@PathVariable Long idx) {
        UserDetailResponse response = new UserDetailResponse();
        // TODO: 예외 처리
        UserDetailDto dto = userService.getUserDetail(idx);
        response.setCode(20000);
        response.setMessage("Success");
        response.setDetail(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/order/{idx}")
    public ResponseEntity<UserOrderResponse> getUserOrder(@PathVariable Long idx) {
        UserOrderResponse response = new UserOrderResponse();
        // TODO: 예외 처리
        List<OrderDto> dtos = userService.getUserOrder(idx);
        response.setCode(20001);
        response.setMessage("Success");
        response.setOrders(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<UserListResponse> getUserList(String name, String email, Integer page) {
        UserListResponse response = new UserListResponse();
        // TODO: 유저 서비스 메서드 호출 & Try-catch
        List<UserListDto> dtos = userService.getUserList(email, name, page);
        response.setCode(30000);
        response.setMessage("Success");
        response.setList(dtos);
        return ResponseEntity.ok(response);
    }
}

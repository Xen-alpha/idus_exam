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
        UserDetailDto dto = userService.getUserDetail(idx);
        if (dto == null) {
            response.setCode(72000);
            response.setMessage("Not found");
            response.setDetail(null);
            return ResponseEntity.notFound().build();
        }
        response.setCode(20000);
        response.setMessage("Success");
        response.setDetail(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/order/{idx}")
    public ResponseEntity<UserOrderResponse> getUserOrder(@PathVariable Long idx) {
        UserOrderResponse response = new UserOrderResponse();
        List<OrderDto> dtos = userService.getUserOrder(idx);
        if (dtos.isEmpty()) {
            response.setCode(70001);
            response.setMessage("Empty");
        } else {
            response.setCode(20001);
            response.setMessage("Success");
        }
        response.setOrders(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<UserListResponse> getUserList(String name, String email, Integer page) {
        UserListResponse response = new UserListResponse();
        // TODO: Try-catch
        List<UserListDto> dtos = userService.getUserList(email, name, page);
        if (dtos.isEmpty()) {
            response.setCode(80000);
            response.setMessage("Empty");
        } else {
            response.setCode(30000);
            response.setMessage("Success");
        }
        response.setList(dtos);
        return ResponseEntity.ok(response);
    }
}

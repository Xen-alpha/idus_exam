package com.example.demo;

import com.example.demo.model.common.BaseResponse;
import com.example.demo.model.user.*;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary="회원 가입", description="회원 정보로 이메일, 실명, 닉네임, 휴대폰 번호, 이메일, 성별을 수집합니다.")
    public ResponseEntity<BaseResponse> signup(@RequestBody SignupRequest request) {
        System.out.println("Signup request detected");
        BaseResponse response = new BaseResponse();
        userService.signup(request);
        response.setSuccess();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/info/{idx}")
    @Operation(summary="유저 정보 보기", description="DB 내 특정 인덱스의 이메일, 실명, 닉네임, 휴대폰 번호, 이메일, 성별을 보여줍니다.")
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
    @Operation(summary="회원 주문 정보 조회", description="DB 내 특정 인덱스 회원이 주문한 물품 목록을 보여줍니다.")
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
    @Operation(summary="전체 회원 조회", description="전체 회원 목록과 각 회원 별 가장 최근 주문 정보를 보여줍니다.")
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

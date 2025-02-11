package com.example.demo.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserListDto {
    @Schema(name="유저 목록 DTO")
    private UserDetailDto userDetail;
    @Schema(name="마지막 주문 DTO")
    private OrderDto lastOrder;
}

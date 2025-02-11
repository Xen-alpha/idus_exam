package com.example.demo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserListDto {
    private UserDetailDto userDetail;
    private OrderDto lastOrder;
}

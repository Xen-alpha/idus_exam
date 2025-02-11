package com.example.demo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailDto {
    private Long idx;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String gender;
}

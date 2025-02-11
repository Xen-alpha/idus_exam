package com.example.demo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupRequest {
    private String email;
    private String username;
    private String name;
    private String password;
    private String gender;
    private String phone;
}

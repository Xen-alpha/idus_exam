package com.example.demo.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResponse {
    private int code;
    private String message;
    public void setSuccess() {
        this.code = 10000;
        this.message = "가입 성공";
    }
}

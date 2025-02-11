package com.example.demo.model.user;

import com.example.demo.model.common.BaseResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailResponse extends BaseResponse {
    private UserDetailDto detail;

    @Override
    public void setSuccess() {
        this.setCode(20000);
        this.setMessage("정보 전달 성공");
    }
}

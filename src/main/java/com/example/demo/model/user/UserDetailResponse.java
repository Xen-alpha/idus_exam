package com.example.demo.model.user;

import com.example.demo.model.common.BaseResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailResponse extends BaseResponse {
    private UserDetailDto detail;
}

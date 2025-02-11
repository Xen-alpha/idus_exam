package com.example.demo.model.user;

import com.example.demo.model.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserListResponse extends BaseResponse {
    private List<UserListDto> list;

    @Override
    public void setSuccess() {
        this.setCode(30000);
        this.setMessage("리스트 전달 성공");
    }

    public void setEmpty() {
        this.setCode(70001);
        this.setMessage("주문 정보 가져옴");
    }
}

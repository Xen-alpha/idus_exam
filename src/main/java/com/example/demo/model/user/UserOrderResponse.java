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
public class UserOrderResponse extends BaseResponse {
    private List<OrderDto> orders;

    @Override
    public void setSuccess() {
        this.setCode(20001);
        this.setMessage("주문 정보 가져옴");
    }

    public void setEmpty() {
        this.setCode(70001);
        this.setMessage("주문 정보 가져옴");
    }
}

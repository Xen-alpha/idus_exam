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
}

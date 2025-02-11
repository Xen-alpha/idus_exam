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
public class UserDetailDto {
    @Schema(name="유저 정보 DB 인덱스")
    private Long idx;
    @Schema(name="실명")
    private String name;
    @Schema(name="별명")
    private String nickname;
    @Schema(name="휴대전화번호")
    private String phone;
    @Schema(name="이메일")
    private String email;
    @Schema(name="성별")
    private String gender;
}

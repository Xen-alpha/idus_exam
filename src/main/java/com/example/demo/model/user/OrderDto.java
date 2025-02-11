package com.example.demo.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    @Schema(name="주문번호")
    private String orderId;
    @Schema(name="주문 상품")
    private String productName;
    @Schema(name="주문 일자")
    private Date datetime;
}

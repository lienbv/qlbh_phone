package com.datn_qlbh.model.request;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}

package com.datn_qlbh.model.response;

import lombok.Data;

import java.util.List;
@Data
public class LoginResponse extends BaseResponse{

    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

}

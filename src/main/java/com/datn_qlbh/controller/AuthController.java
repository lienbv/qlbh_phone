package com.datn_qlbh.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.datn_qlbh.entity.RefreshToken;
import com.datn_qlbh.entity.User;
import com.datn_qlbh.jwt.JwtUtility;
import com.datn_qlbh.model.request.LoginRequest;
import com.datn_qlbh.model.request.TokenRefreshRequest;
import com.datn_qlbh.model.response.LoginResponse;
import com.datn_qlbh.model.response.TokenRefreshResponse;
import com.datn_qlbh.repository.UserRepo;
import com.datn_qlbh.service.Impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthServiceImpl authService;
    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public LoginResponse login(@Validated @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Validated @RequestBody TokenRefreshRequest request) {
        return this.authService.refreshtoken(request);
    }

}

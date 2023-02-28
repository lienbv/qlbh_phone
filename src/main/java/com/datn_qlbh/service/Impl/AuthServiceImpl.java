package com.datn_qlbh.service.Impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.datn_qlbh.entity.RefreshToken;
import com.datn_qlbh.entity.User;
import com.datn_qlbh.jwt.JwtUtility;
import com.datn_qlbh.model.Status;
import com.datn_qlbh.model.request.LoginRequest;
import com.datn_qlbh.model.request.TokenRefreshRequest;
import com.datn_qlbh.model.response.LoginResponse;
import com.datn_qlbh.model.response.TokenRefreshResponse;
import com.datn_qlbh.repository.RefreshTokenRepo;
import com.datn_qlbh.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl {
    private final JwtUtility jwtUtility;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepo refreshTokenRepository;
    private final UserRepo userRepo;

    @Value("${jwt.app.jwtExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    public AuthServiceImpl(JwtUtility jwtUtility, AuthenticationManager authenticationManager, RefreshTokenRepo refreshTokenRepository,
                           UserRepo userRepo) {
        this.jwtUtility = jwtUtility;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepo = userRepo;
    }
    public LoginResponse login(@Validated @RequestBody LoginRequest loginRequest) {

        LoginResponse response = new LoginResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtility.generateJwtToken(loginRequest.getUsername());
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication
                    .getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            User user = this.userRepo.findByUsername(userDetails.getUsername());

            RefreshToken refreshToken = this.createRefreshToken(user.getId());
            response.setToken(jwt);
            response.setRefreshToken(refreshToken.getToken());
            response.setUsername(userDetails.getUsername());
            response.setRoles(roles);
            response.getStatus().setStatus(Status.Success);
            response.getStatus().setMessage("Thành công");
            return response;
        } catch (BadCredentialsException ex) {
            response.getStatus().setStatus(Status.Fail);
            response.getStatus().setMessage("Tài khoản mật khẩu không chính xác");
            return response;
        }

    }
    public ResponseEntity<?> refreshtoken(@Validated @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        Optional<RefreshToken> optionalRefreshToken = this.findByToken(requestRefreshToken);

        if (optionalRefreshToken.isPresent()) {
            RefreshToken refreshToken = optionalRefreshToken.get();

            if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(refreshToken);
                throw new RuntimeException (refreshToken.getToken());
            }

            User user = this.userRepo.findById(refreshToken.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User không tồn tại"));
            String token = jwtUtility.generateJwtToken(user.getUsername());
            return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        } else {
            throw new TokenExpiredException(requestRefreshToken + "Refresh token not in database", Instant.now());
        }
    }
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();
        User user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User không tồn tại"));

        refreshToken.setUserId(user.getId());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException (token.getToken());
        }

        return token;
    }
    @Transactional
    public int deleteByUserId(int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User không tồn tại"));
        return refreshTokenRepository.deleteByUserId(user.getId());
    }
}

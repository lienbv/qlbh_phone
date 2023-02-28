package com.datn_qlbh.repository;

import com.datn_qlbh.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer>, JpaSpecificationExecutor<RefreshToken> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUserId(int userId);
}
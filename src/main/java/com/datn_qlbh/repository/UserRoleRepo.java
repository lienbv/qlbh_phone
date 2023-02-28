
package com.datn_qlbh.repository;

import com.datn_qlbh.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaSpecificationExecutor<UserRole>, JpaRepository<UserRole, Integer> {
    List<String> findByRole(int id);
}

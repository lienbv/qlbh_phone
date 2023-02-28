package com.datn_qlbh.repository;

import com.datn_qlbh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaSpecificationExecutor<User>, JpaRepository<User, Integer> {

    User findByUsername(String user);


}

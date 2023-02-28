package com.datn_qlbh.repository;

import com.datn_qlbh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>{

	@Query(value = "SELECT role.name FROM role JOIN user_role on role.ID= user_role.ID_ROLE WHERE user_role.ID_USER= ?1",  nativeQuery = true)
	List<String> findByRole(int idUserRole);
}

package com.datn_qlbh.service;

import com.datn_qlbh.entity.User;
import com.datn_qlbh.repository.RoleRepo;
import com.datn_qlbh.repository.UserRepo;
import com.datn_qlbh.repository.UserRoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplUserDetailService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplUserDetailService.class);
	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	private final UserRoleRepo userRoleRepo;
	@Autowired
	public UserServiceImplUserDetailService(UserRepo userRepo, RoleRepo roleRepo, UserRoleRepo userRoleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.userRoleRepo = userRoleRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			User account = userRepo.findByUsername(username);
			List<String> lstroles = roleRepo.findByRole(account.getId());
			String password = account.getPassword();

			List<GrantedAuthority> grantList = new ArrayList<>();

			for (String grantedAuthority : lstroles) {
				GrantedAuthority authority = new SimpleGrantedAuthority(grantedAuthority);
				grantList.add(authority);
			}
			UserDetails userDetail = (UserDetails) new UserDetailImplUser(username, password, grantList);
			logger.info(userDetail.toString());

			return userDetail;
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + " not found!");

		}
	}


}

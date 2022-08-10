package com.mockproject.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mockproject.constant.RoleConstant;
import com.mockproject.entity.Roles;
import com.mockproject.entity.Users;
import com.mockproject.repository.UserRepo;
import com.mockproject.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private RoleServiceImpl roleService;
	
	@Override
	public List<Users> findAll() {
		return null;
	}

	@Override
	public Users doLogin(Users userRequest) {
		Users userResponse = repo.findByUsername(userRequest.getUsername());
		if (ObjectUtils.isNotEmpty(userResponse)) {
			boolean checkPassword = bCrypt.matches(userRequest.getHashPassword(), userResponse.getHashPassword());
			return checkPassword ? userResponse : null;
		}
		return null;
	}

	@Override
	@Transactional(rollbackOn = {Throwable.class})
	public Users save(Users user) throws SQLException {
		Roles roles = roleService.findByDescription(RoleConstant.USER);
		user.setRoles(roles);
		user.setIsDelete(Boolean.FALSE);
		
		String hashPassword = bCrypt.encode(user.getHashPassword());
		user.setHashPassword(hashPassword);
		return repo.saveAndFlush(user);
	}



}

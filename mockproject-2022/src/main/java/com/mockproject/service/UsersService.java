package com.mockproject.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mockproject.entity.Users;

public interface UsersService {
	List<Users> findAll();

	Users doLogin(Users userRequest);
	Users save(Users user) throws SQLException;
	
}

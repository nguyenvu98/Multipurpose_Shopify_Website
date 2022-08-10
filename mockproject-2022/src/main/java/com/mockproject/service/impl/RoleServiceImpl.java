package com.mockproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.constant.RoleConstant;
import com.mockproject.entity.Roles;
import com.mockproject.repository.RoleRepo;
import com.mockproject.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepo repo;
	
	@Override
	public Roles findByDescription(String description) {
		return repo.findByDescription(description);
	}

}

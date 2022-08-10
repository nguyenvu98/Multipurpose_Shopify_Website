package com.mockproject.service;

import com.mockproject.entity.Roles;

public interface RoleService {
	Roles findByDescription(String description);
}

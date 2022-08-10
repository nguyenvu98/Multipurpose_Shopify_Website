package com.mockproject.repository;

import javax.management.relation.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.Roles;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Long> {
	Roles findByDescription(String description);
}

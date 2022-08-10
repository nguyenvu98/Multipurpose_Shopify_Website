package com.mockproject.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.Products;
import com.mockproject.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

//	@Query(value = "select username,hashPassword from users",nativeQuery = true)
	

	
	Users findByUsername(String username);

}

package com.mockproject.service;

import java.util.List;
import java.util.Map;

import com.mockproject.entity.Products;

public interface ProductsService {

	List<Products> findAll();
	Products findById(Long id);
	Products findBySlug(String slug);
	void updateQuantity(Integer newQuantity, Long id);
}

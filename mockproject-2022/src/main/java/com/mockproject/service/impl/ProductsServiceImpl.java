package com.mockproject.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.entity.Products;
import com.mockproject.repository.ProductRepo;
import com.mockproject.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	@Autowired
	private ProductRepo repo;

//	@Override
//	public List<Products> findAll() {
//		return repo.findByIsDeleteAndQuantityGreaterThan(Boolean.FALSE,0);
//	}
	
	@Override
	public List<Products> findAll() {
		return repo.findAllAvailable();
	}

	@Override
	public Products findById(Long id) {
		Optional<Products> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Products findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Transactional(value =  TxType.REQUIRED)
	@Override
	public void updateQuantity(Integer newQuantity, Long id) {
		repo.updateQuantity(newQuantity, id);
	}





	
}

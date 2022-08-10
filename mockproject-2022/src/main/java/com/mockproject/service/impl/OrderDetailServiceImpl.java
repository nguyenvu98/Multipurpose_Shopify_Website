package com.mockproject.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.dto.CartDetailDto;
import com.mockproject.repository.OrderDetailRepo;
import com.mockproject.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

	@Autowired
	private OrderDetailRepo repo;
	
	@Transactional(value = TxType.REQUIRED)
	@Override
	public void insert(CartDetailDto cartDetailDto) {
		repo.insert(cartDetailDto);
	}

}

package com.mockproject.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.entity.Orders;
import com.mockproject.repository.OrderRepo;
import com.mockproject.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepo repo;
	
	@Transactional(value =  TxType.REQUIRED)
	@Override
	public Orders insert(Orders orders) {
		return repo.saveAndFlush(orders);
	}
	
}

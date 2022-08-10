package com.mockproject.service.impl;

import java.util.HashMap;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.dto.CartDetailDto;
import com.mockproject.dto.CartDto;
import com.mockproject.entity.Orders;
import com.mockproject.entity.Products;
import com.mockproject.entity.Users;
import com.mockproject.service.CartService;
import com.mockproject.service.OrderDetailService;
import com.mockproject.service.OrderService;
import com.mockproject.service.ProductsService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		// 1- them moi
		// 2- update:
		//		2.1- cong don
		//		2.2- thay the hoan toan (isReplace = true)
		// 3- delete (update vs quantity = 0)
		Products products = productsService.findById(productId);
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		
		if (!details.containsKey(productId)) {
			CartDetailDto cartDetailDto = createCartDetailDto(products, quantity);
			details.put(productId, cartDetailDto);
		} else if(quantity > 0) {
			if (isReplace) {
				CartDetailDto productNeedToUpdate = details.get(productId);
				productNeedToUpdate.setQuantity(quantity);
				details.put(productId, productNeedToUpdate);
			}else {
				Integer currentQuantity = details.get(productId).getQuantity();
				Integer newQuantity = currentQuantity + quantity;
				details.get(productId).setQuantity(newQuantity);
			}
		}else {
			details.remove(productId);
		}
		cart.setTotalQuantity(getTotalQuantity(cart));

		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}
	
	private CartDetailDto createCartDetailDto(Products products, Integer quantity) {
		CartDetailDto cartDetailDto = new CartDetailDto();
		cartDetailDto.setProductId(products.getId());
		cartDetailDto.setName(products.getName());
		cartDetailDto.setQuantity(quantity);
		cartDetailDto.setPrice(products.getPrice());
		cartDetailDto.setImgUrl(products.getImgUrl());
		cartDetailDto.setSlug(products.getSlug());
		return cartDetailDto;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		Integer totalQuantity = 0;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalQuantity += cartDetail.getQuantity();
		}
		return totalQuantity;
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		Double totalPrice = 0D;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalPrice += cartDetail.getQuantity();
		}
		return totalPrice;
	}

	@Transactional(rollbackOn = {Exception.class})
	@Override
	public void insert(CartDto cartDto, Users users, String address, String phone) throws Exception {
		Orders orders = new Orders();
		orders.setUsers(users);
		orders.setAddress(address);
		orders.setPhone(phone);
		
		Orders orderResponse = orderService.insert(orders);
		if (ObjectUtils.isEmpty(orderResponse)) {
			throw new Exception("Insert into order table failed");
		}
		// insert vao bang ORDER_DETAILS
		for(CartDetailDto cartDetailDto : cartDto.getDetails().values()) {
			cartDetailDto.setOrderId(orderResponse.getId());
			orderDetailService.insert(cartDetailDto);
			
			// update new quantity cho bang PRODUCTS
			Products product = productsService.findById(cartDetailDto.getProductId());
			Integer newQuantity = product.getQuantity() - cartDetailDto.getQuantity();
			productsService.updateQuantity(newQuantity, product.getId());
		}
	}

	
	

}

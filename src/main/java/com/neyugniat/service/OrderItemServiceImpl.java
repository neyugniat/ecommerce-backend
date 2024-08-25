package com.neyugniat.service;

import com.neyugniat.model.OrderItem;
import com.neyugniat.repository.OrderItemRepository;

public class OrderItemServiceImpl implements IOrderItemService {

	private OrderItemRepository orderItemRepository;

	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		super();
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {

		return orderItemRepository.save(orderItem);
	}

}

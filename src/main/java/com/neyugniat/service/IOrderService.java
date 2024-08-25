package com.neyugniat.service;

import java.util.List;

import com.neyugniat.exception.OrderException;
import com.neyugniat.model.Address;
import com.neyugniat.model.Order;
import com.neyugniat.model.User;

public interface IOrderService {

	public Order createOrder(User user, Address shippingAddress);

	public Order findOrderById(Long orderId) throws OrderException;

	public Order placedOrder(Long orderId) throws OrderException;

	public Order confirmedOrder(Long orderId) throws OrderException;

	public Order shippedOrder(Long orderId) throws OrderException;

	public Order deliveredOrder(Long orderId) throws OrderException;

	public Order canceledOrder(Long orderId) throws OrderException;

	public List<Order> userOrderHistory(Long userId);

	public List<Order> getAllOrders();

	public void deleteOrder(Long orderId) throws OrderException;
}

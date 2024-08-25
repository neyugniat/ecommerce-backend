package com.neyugniat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neyugniat.exception.OrderException;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.Address;
import com.neyugniat.model.Order;
import com.neyugniat.model.User;
import com.neyugniat.service.IOrderService;
import com.neyugniat.service.IUserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException {

		User user = userService.findUserProfileByJwt(jwt);

		Order order = orderService.createOrder(user, shippingAddress);

		System.out.println("order " + order);

		return new ResponseEntity<Order>(order, HttpStatus.CREATED);

	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> findOrderById(@PathVariable("orderId") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserException, OrderException {
		User user = userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.findOrderById(orderId);
		
		return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt)
			throws UserException {
		User user = userService.findUserProfileByJwt(jwt);

		List<Order> orders = orderService.userOrderHistory(user.getId());

		return ResponseEntity.ok(orders);
	}

}

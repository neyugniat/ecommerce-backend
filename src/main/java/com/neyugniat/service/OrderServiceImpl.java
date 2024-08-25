package com.neyugniat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.neyugniat.exception.OrderException;
import com.neyugniat.model.Address;
import com.neyugniat.model.Cart;
import com.neyugniat.model.CartItem;
import com.neyugniat.model.Order;
import com.neyugniat.model.OrderItem;
import com.neyugniat.model.User;
import com.neyugniat.repository.AddressRepository;
import com.neyugniat.repository.CartRepository;
import com.neyugniat.repository.OrderItemRepository;
import com.neyugniat.repository.OrderRepository;
import com.neyugniat.repository.UserRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	private OrderRepository orderRepository;
	private CartRepository cartRepository;
	private ICartService cartService;
	private IProductService productService;
	private AddressRepository addressRepository;
	private OrderItemRepository orderItemRepository;
	private UserRepository userRepository;

	

	public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, ICartService cartService,
			IProductService productService, AddressRepository addressRepository,
			OrderItemRepository orderItemRepository, UserRepository userRepository) {
		super();
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.cartService = cartService;
		this.productService = productService;
		this.addressRepository = addressRepository;
		this.orderItemRepository = orderItemRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Address address = addressRepository.save(shippingAddress);
		user.getAddresses().add(address);
		userRepository.save(user);

		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();

			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountPrice());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);

			orderItems.add(createdOrderItem);
		}

		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());

		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepository.save(createdOrder);

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}

		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt = orderRepository.findById(orderId);
		
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("Order with id: " + orderId + " not existed!");
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERIED");
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		List<Order> orders = orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		
		orderRepository.deleteById(orderId);
	}

}

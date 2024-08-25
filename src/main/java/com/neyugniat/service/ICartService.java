package com.neyugniat.service;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Cart;
import com.neyugniat.model.User;
import com.neyugniat.request.AddItemRequest;

public interface ICartService {

	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

	public Cart findUserCart(Long userId);
}

package com.neyugniat.service;

import com.neyugniat.exception.CartItemException;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.Cart;
import com.neyugniat.model.CartItem;
import com.neyugniat.model.Product;

public interface ICartItemService {
	public CartItem createCartItem(CartItem cartItem);

	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}

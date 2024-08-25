package com.neyugniat.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.neyugniat.exception.CartItemException;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.Cart;
import com.neyugniat.model.CartItem;
import com.neyugniat.model.Product;
import com.neyugniat.model.User;
import com.neyugniat.repository.CartItemRepository;
import com.neyugniat.repository.CartRepository;

@Service
public class CartItemServiceImpl implements ICartItemService {

	private CartItemRepository cartItemRepository;
	private UserServiceImpl userServiceImpl;
	private CartRepository cartRepository;

	public CartItemServiceImpl(CartItemRepository cartItemRepository, UserServiceImpl userServiceImpl,
			CartRepository repository) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.userServiceImpl = userServiceImpl;
		this.cartRepository = cartRepository;
	}

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountPrice(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());

		CartItem createdCartItem = cartItemRepository.save(cartItem);
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem updatedCartItem) throws CartItemException, UserException {
	    // Find the existing CartItem by ID
	    CartItem existingCartItem = findCartItemById(id);
	    if (existingCartItem == null) {
	        throw new CartItemException("CartItem not found with ID: " + id);
	    }

	    // Ensure the user exists
	    User user = userServiceImpl.findUserById(userId);
	    if (user == null) {
	        throw new UserException("User not found with ID: " + userId);
	    }

	    // Update fields of the existing CartItem
	    existingCartItem.setQuantity(updatedCartItem.getQuantity());

	    // Ensure price calculations are correct
	    Product product = existingCartItem.getProduct();
	    if (product != null) {
	        existingCartItem.setPrice(existingCartItem.getQuantity() * product.getPrice());
	        existingCartItem.setDiscountPrice(product.getDiscountPrice() * existingCartItem.getQuantity());
	    }

	    // Save the updated CartItem
	    return cartItemRepository.save(existingCartItem);
	}


	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);

		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
	    // Check if CartItem exists
	    CartItem cartItem = findCartItemById(cartItemId);
	    if (cartItem == null) {
	        throw new CartItemException("CartItem not found with ID: " + cartItemId);
	    }

	    // Check if user exists
	    User itemOwner = userServiceImpl.findUserById(cartItem.getUserId());
	    if (itemOwner == null) {
	        throw new UserException("User not found with ID: " + cartItem.getUserId());
	    }

	    User reqUser = userServiceImpl.findUserById(userId);
	    if (reqUser == null) {
	        throw new UserException("Requesting user not found with ID: " + userId);
	    }

	    // Check if the requesting user is the owner of the CartItem
	    if (itemOwner.getId().equals(reqUser.getId())) {
	        // Remove the CartItem
	        cartItemRepository.deleteCartItemById(cartItemId);
	    } else {
	        throw new UserException("You can't remove another user's items");
	    }
	}


	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("Cart Item not found with id: " + cartItemId);
	}

}

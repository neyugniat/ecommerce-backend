package com.neyugniat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neyugniat.exception.ProductException;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.Cart;
import com.neyugniat.model.User;
import com.neyugniat.request.AddItemRequest;
import com.neyugniat.response.ApiResponse;
import com.neyugniat.service.ICartService;
import com.neyugniat.service.IUserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

	@Autowired
	private IUserService userService;

	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		
		if (cart != null) {
	        System.out.println("Cart ID: " + cart.getId());
	        System.out.println("Cart Items: " + cart.getCartItems());
	        
	    } else {
	        System.out.println("Cart not found for user ID: " + user.getId());
	    }

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
	        @RequestHeader("Authorization") String jwt) {
	    ApiResponse res = new ApiResponse();
	    try {
	        // Retrieve user profile based on JWT
	        User user = userService.findUserProfileByJwt(jwt);

	        // Add item to cart
	        cartService.addCartItem(user.getId(), req);

	        res.setMessage("Item added to cart");
	        res.setStatus(true);
	        return new ResponseEntity<>(res, HttpStatus.OK);

	    } catch (UserException e) {
	        // Handle user-related exceptions
	        res.setMessage("User not found or invalid.");
	        res.setStatus(false);
	        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

	    } catch (ProductException e) {
	        // Handle product-related exceptions
	        res.setMessage("Product not found or invalid.");
	        res.setStatus(false);
	        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

	    } catch (Exception e) {
	        // Handle other unexpected exceptions
	        res.setMessage("An unexpected error occurred.");
	        res.setStatus(false);
	        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}

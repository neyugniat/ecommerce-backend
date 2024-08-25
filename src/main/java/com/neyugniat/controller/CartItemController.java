package com.neyugniat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neyugniat.exception.CartItemException;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.User;
import com.neyugniat.response.ApiResponse;
import com.neyugniat.service.ICartItemService;
import com.neyugniat.service.IUserService;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {
	@Autowired
	private ICartItemService carItemService;

	@Autowired
	private IUserService userService;

	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
	        @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
	    try {
	        // Retrieve the user associated with the JWT token
	        User user = userService.findUserProfileByJwt(jwt);
	        
	        // Remove the specified cart item
	        carItemService.removeCartItem(user.getId(), cartItemId);
	        System.out.println("userID: " + user.getId() + ", cartItemId: " + cartItemId);
	        
	        // Prepare and return the response
	        ApiResponse res = new ApiResponse();
	        res.setMessage("Item deleted successfully");
	        res.setStatus(true);
	        
	        return new ResponseEntity<>(res, HttpStatus.OK);
	    } catch (UserException | CartItemException e) {
	        // Handle exceptions and return appropriate error response
	        ApiResponse res = new ApiResponse();
	        res.setMessage(e.getMessage());
	        res.setStatus(false);
	        
	        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	    }
	}

}

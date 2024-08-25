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

import com.neyugniat.exception.ProductException;
import com.neyugniat.exception.RatingException;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.Rating;
import com.neyugniat.model.User;
import com.neyugniat.request.RatingRequest;
import com.neyugniat.service.IRatingService;
import com.neyugniat.service.IUserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	@Autowired
	private IUserService userService;

	@Autowired
	private IRatingService ratingService;

	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);

		Rating rating = ratingService.createRating(req, user);

		return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Rating> ratings = ratingService.getProductsRating(productId);
		
		return new ResponseEntity<>(ratings, HttpStatus.ACCEPTED);	
	}
}

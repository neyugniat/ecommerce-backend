package com.neyugniat.service;

import java.util.List;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Rating;
import com.neyugniat.model.User;
import com.neyugniat.request.RatingRequest;

public interface IRatingService {
	public Rating createRating(RatingRequest req, User user) throws ProductException;

	public List<Rating> getProductsRating(Long productId);
}

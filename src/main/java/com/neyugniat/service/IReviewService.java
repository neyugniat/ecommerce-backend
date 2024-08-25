package com.neyugniat.service;

import java.util.List;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Review;
import com.neyugniat.model.User;
import com.neyugniat.request.ReviewRequest;

public interface IReviewService {
	public Review createReview(ReviewRequest req, User user) throws ProductException;

	public List<Review> getAllReview(Long productId);
}

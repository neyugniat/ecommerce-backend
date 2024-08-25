package com.neyugniat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Product;
import com.neyugniat.model.Review;
import com.neyugniat.model.User;
import com.neyugniat.repository.ProductRepository;
import com.neyugniat.repository.ReviewRepository;
import com.neyugniat.request.ReviewRequest;

@Service
public class ReviewServiceImpl implements IReviewService {

	private ReviewRepository reviewRepository;
	private IProductService productService;
	private ProductRepository productRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository, IProductService productService,
			ProductRepository productRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.productService = productService;
		this.productRepository = productRepository;
	}

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		Review review = new Review();
		review.setReview(req.getReview());
		review.setUser(user);
		review.setProduct(product);
		review.setCreatedAt(LocalDateTime.now());

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {

		return reviewRepository.getAllProductsReview(productId);
	}

}

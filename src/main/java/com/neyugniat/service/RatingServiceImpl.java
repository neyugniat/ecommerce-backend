package com.neyugniat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Product;
import com.neyugniat.model.Rating;
import com.neyugniat.model.User;
import com.neyugniat.repository.RatingRepository;
import com.neyugniat.request.RatingRequest;

@Service
public class RatingServiceImpl implements IRatingService {

	private RatingRepository ratingRepository;
	private IProductService productService;

	public RatingServiceImpl(RatingRepository ratingRepository, IProductService productService) {
		super();
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {

		return ratingRepository.getAllProductsRating(productId);
	}

}

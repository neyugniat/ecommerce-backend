package com.neyugniat.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Product;
import com.neyugniat.request.CreateProductRequest;

public interface IProductService {
	public Product createProduct(CreateProductRequest req);

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product reqProduct) throws ProductException;

	public Product findProductById(Long id) throws ProductException;

	public List<Product> findProductByCategory(String category);

	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

	public List<Product> findAllProducts();
}

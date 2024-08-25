package com.neyugniat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Category;
import com.neyugniat.model.Product;
import com.neyugniat.repository.CategoryRepository;
import com.neyugniat.repository.ProductRepository;
import com.neyugniat.request.CreateProductRequest;

@Service
public class ProductServiceImpl implements IProductService {

	private ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;

	public ProductServiceImpl(ProductRepository productRepository, UserService userService,
			CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.userService = userService;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Product createProduct(CreateProductRequest req) {
		// Find or create the top-level category
		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
		if (topLevel == null) {
			topLevel = new Category();
			topLevel.setName(req.getTopLevelCategory());
			topLevel.setLevel(1);
			topLevel = categoryRepository.save(topLevel);
		}

		// Find or create the second-level category
		Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
		if (secondLevel == null) {
			secondLevel = new Category();
			secondLevel.setName(req.getSecondLevelCategory());
			secondLevel.setParentCategory(topLevel);
			secondLevel.setLevel(2);
			secondLevel = categoryRepository.save(secondLevel);
		}

		// Find or create the third-level category
		Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
				secondLevel.getName());
		if (thirdLevel == null) {
			thirdLevel = new Category();
			thirdLevel.setName(req.getThirdLevelCategory());
			thirdLevel.setParentCategory(secondLevel);
			thirdLevel.setLevel(3);
			thirdLevel = categoryRepository.save(thirdLevel);
		}

		// Create and save the product
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDesciprtion(req.getDescription());
		product.setDiscountPrice(req.getDiscountedPrice());
		product.setDiscountPercent(req.getDiscountedPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel); // Assign the product to the third-level category
		product.setCreatedAt(LocalDateTime.now());

		return productRepository.save(product);
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product reqProduct) throws ProductException {
		Product product = findProductById(productId);

		if (reqProduct.getQuantity() != 0) {
			product.setQuantity(reqProduct.getQuantity());
		}

		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt = productRepository.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id- " + id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {

		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		if (!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);

		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

		return filteredProducts;
	}

	@Override
	public List<Product> findAllProducts() {

		return productRepository.findAll();
	}

}

package com.neyugniat.service;

import org.springframework.stereotype.Service;

import com.neyugniat.exception.ProductException;
import com.neyugniat.model.Cart;
import com.neyugniat.model.CartItem;
import com.neyugniat.model.Product;
import com.neyugniat.model.User;
import com.neyugniat.repository.CartRepository;
import com.neyugniat.request.AddItemRequest;

@Service
public class CartServiceImpl implements ICartService {

	private CartRepository cartRepository;
	private ICartItemService cartItemService;
	private IProductService productService;

	public CartServiceImpl(CartRepository cartRepository, ICartItemService cartItemService,
			IProductService productService) {
		super();
		this.cartRepository = cartRepository;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);

		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
	    Cart cart = cartRepository.findByUserId(userId);
	    if (cart == null) {
	        throw new ProductException("Cart not found for user ID: " + userId);
	    }

	    Product product = productService.findProductById(req.getProductId());
	    if (product == null) {
	        throw new ProductException("Product not found for ID: " + req.getProductId());
	    }

	    CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
	    if (isPresent == null) {
	        CartItem cartItem = new CartItem();
	        cartItem.setProduct(product);
	        cartItem.setQuantity(req.getQuantity());
	        cartItem.setUserId(userId);
	        cartItem.setCart(cart);

	        int price = req.getQuantity() * product.getDiscountPrice();
	        cartItem.setPrice(price);
	        cartItem.setSize(req.getSize());
	        
	        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
	        cart.getCartItems().add(createdCartItem);
	    }
	    return "Item added to cart";
	}



	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice += cartItem.getPrice();
			totalDiscountedPrice += cartItem.getDiscountPrice();
			totalItem += cartItem.getQuantity();
		}
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(totalItem);
		cart.setDiscount(totalPrice - totalDiscountedPrice);

		return cartRepository.save(cart);
	}

}

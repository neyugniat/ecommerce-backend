package com.neyugniat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neyugniat.model.Cart;
import com.neyugniat.model.CartItem;
import com.neyugniat.model.Product;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product AND ci.size = :size AND ci.userId = :userId")
	CartItem isCartItemExist(@Param("cart") Cart cart, 
	                         @Param("product") Product product, 
	                         @Param("size") String size, 
	                         @Param("userId") Long userId);

	@Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.id = :cartItemId")
    void deleteCartItemById(@Param("cartItemId") Long cartItemId);
}

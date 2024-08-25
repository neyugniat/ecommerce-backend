package com.neyugniat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neyugniat.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}

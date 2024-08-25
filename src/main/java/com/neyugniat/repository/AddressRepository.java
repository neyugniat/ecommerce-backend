package com.neyugniat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neyugniat.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
}

package com.neyugniat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neyugniat.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);
}

package com.neyugniat.service;

import com.neyugniat.exception.UserException;
import com.neyugniat.model.User;

public interface IUserService {
	public User findUserById(Long userId) throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;

	public User findUserByEmail(String email) throws UserException;
}

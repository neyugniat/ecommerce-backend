package com.neyugniat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neyugniat.config.JwtProvider;
import com.neyugniat.exception.UserException;
import com.neyugniat.model.User;
import com.neyugniat.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	private UserRepository userRepository;
	private JwtProvider jwtProvider;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {

		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		} else {

		}

		throw new UserException("User not found with ID: " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = this.jwtProvider.getEmailFromToken(jwt);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("User not found with email: " + email);
		}

		return user;
	}

	@Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return user;
    }

}

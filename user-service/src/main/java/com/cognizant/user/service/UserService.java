package com.cognizant.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.cqrs.events.UserDeleteEvent;
import com.cognizant.user.exception.UserNotFoundException;
import com.cognizant.user.mapper.UserMapper;
import com.cognizant.user.model.User;
import com.cognizant.user.payload.UserResponseInfo;
import com.cognizant.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private UserMapper buyerMapper;
	
	UserService(UserRepository userRepository, UserMapper buyerMapper) {
		this.userRepository = userRepository;
		this.buyerMapper = buyerMapper;
	}

	public UserResponseInfo addUser(UserAddEvent event) {
		User user = buyerMapper.toUser(event);
		user.setActive(true);
		user = userRepository.save(user);
		return buyerMapper.toUserResponseInfo(user);
	}
	
	public boolean deleteUser(UserDeleteEvent event) throws UserNotFoundException {
		Optional<User> userOp = userRepository.findById(event.getUserId());
		if(userOp.isEmpty()) {
			throw new UserNotFoundException("No user found with user id "+event.getUserId());
		}
		userRepository.delete(userOp.get());
		return true;
	}
	
	public UserResponseInfo getUserByUserId(String userId) throws UserNotFoundException {
		Optional<User> userOp = userRepository.findById(userId);
		if(userOp.isEmpty()) {
			throw new UserNotFoundException("No user found with user id "+userId);
		}
		return buyerMapper.toUserResponseInfo(userOp.get());
	}
	
	public UserResponseInfo getUserByEmailId(String emailId) throws UserNotFoundException {
		Optional<User> userOp = userRepository.findByEmail(emailId);
		if(userOp.isEmpty()) {
			throw new UserNotFoundException("No user found with email id "+emailId);
		}
		return buyerMapper.toUserResponseInfo(userOp.get());
	}
	
}

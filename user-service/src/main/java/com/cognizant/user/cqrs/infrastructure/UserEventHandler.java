package com.cognizant.user.cqrs.infrastructure;

import org.springframework.stereotype.Service;

import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.cqrs.events.UserDeleteEvent;
import com.cognizant.user.exception.UserNotFoundException;
import com.cognizant.user.service.UserService;

@Service
public class UserEventHandler implements EventHandler{

	private UserService userService;
	
	UserEventHandler(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void on(UserAddEvent event) {
		userService.addUser(event);
	}
	
	@Override
	public void on(UserDeleteEvent event) {
		userService.deleteUser(event);
	}

}

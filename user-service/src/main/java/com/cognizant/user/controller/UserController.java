package com.cognizant.user.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognizant.cqrs.core.infrastructure.CommandDispatcher;
import com.cognizant.user.cqrs.commands.UserAddCommand;
import com.cognizant.user.cqrs.commands.UserDeleteCommand;
import com.cognizant.user.exception.UserNotFoundException;
import com.cognizant.user.mapper.UserMapper;
import com.cognizant.user.payload.ApiResponse;
import com.cognizant.user.payload.UserRequestInfo;
import com.cognizant.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	private UserService userService;
	private CommandDispatcher commandDispatcher;
	private UserMapper userMapper;
	
	UserController(UserService userService, CommandDispatcher commandDispatcher, UserMapper userMapper) {
		this.userService = userService;
		this.commandDispatcher = commandDispatcher;
		this.userMapper = userMapper;
	}
	
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody @Valid UserRequestInfo userRequestInfo) throws Exception {
		log.debug("productAddRequestInfo [" + userRequestInfo + "]");
		UserAddCommand command = userMapper.toUserAddCommand(userRequestInfo);
		command.setId(UUID.randomUUID().toString());
		commandDispatcher.send(command);
		
//		URI location = new URI("/v1/user/email/"+userRequestInfo.getEmail());
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/email/{emailId}")
                .buildAndExpand(userRequestInfo.getEmail())
                .toUri();
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setLocation(location);
	    
		return new ResponseEntity<>(ApiResponse.ofSuccess(201, "User added successfully"), responseHeaders , HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@NotBlank(message = "userId") @PathVariable String userId) {
		log.debug("deleteUser  >>");
		log.debug("userId [" + userId + "]");
		commandDispatcher.send(UserDeleteCommand.builder().userId(userId).build());
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, "User Deleted successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserByUserId(@NotBlank(message = "userId") @PathVariable String userId) throws UserNotFoundException {
		log.debug("getUserByUserId  >>");
		log.debug("userId [" + userId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, userService.getUserByUserId(userId)), HttpStatus.OK);
	}
	
	@GetMapping("/email/{emailId}")
	public ResponseEntity<?> getUserByEmailId(@NotBlank(message = "emailId") @PathVariable String emailId) throws UserNotFoundException {
		log.debug("getUserByEmailId  >>");
		log.debug("emailId [" + emailId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, userService.getUserByEmailId(emailId)), HttpStatus.OK);
	}

}

package com.cognizant.user.mapper;

import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cognizant.user.cqrs.commands.UserAddCommand;
import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.model.User;
import com.cognizant.user.payload.UserRequestInfo;
import com.cognizant.user.payload.UserResponseInfo;

@Mapper(componentModel = "spring")
public interface UserMapper {

	public static UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

	public User toUser(UserAddEvent event);

	public UserResponseInfo toUserResponseInfo(User user);

	public UserAddCommand toUserAddCommand(UserRequestInfo userRequestInfo);
	
	
	
}

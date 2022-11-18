package com.cognizant.bid.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cognizant.bid.meta.UserType;
import com.cognizant.bid.validation.ValidEmail;
import com.cognizant.bid.validation.ValidEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestInfo {

	@NotNull( message = "First Name cannot be null")
	@NotBlank( message = "First Name cannot be blank")
	@Size(min = 5, message = "First Name cannot be less than 5 characters")
	@Size(max = 30, message = "First Name cannot be greater than 30 characters")
	private String firstName;
	
	@NotNull( message = "Last Name cannot be null")
	@NotBlank( message = "Last Name cannot be blank")
	@Size(min = 3, message = "Last Name cannot be less than 3 characters")
	@Size(max = 30, message = "Last Name cannot be greater than 30 characters")
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	@Size(min = 10, max = 10, message = "Phone no must be of length 10")
	@Pattern( regexp = "^\\d{10}$" )
	private String phone;
	
	@NotNull( message = "Email cannot be null")
	@NotBlank( message = "Email Name cannot be blank")
	@ValidEmail
	private String email;
	
	@ValidEnum(message = "Invalid user type provided", isRequired = false, enumClass = UserType.class)
	private String userType;
}

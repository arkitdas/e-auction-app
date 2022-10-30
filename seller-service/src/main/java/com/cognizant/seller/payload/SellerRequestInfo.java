package com.cognizant.seller.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class SellerRequestInfo {

	@NotNull( message = "First Name cannot be null")
	@NotBlank( message = "First Name cannot be blank")
	@Min(value = 5, message = "First Name cannot be less than 5 characters")
	@Max(value = 30, message = "First Name cannot be greater than 30 characters")
	private String firstName;
	
	@NotNull( message = "Last Name cannot be null")
	@NotBlank( message = "Last Name cannot be blank")
	@Min(value = 3, message = "Last Name cannot be less than 3 characters")
	@Max(value = 30, message = "Last Name cannot be greater than 30 characters")
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
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
	private String email;
}

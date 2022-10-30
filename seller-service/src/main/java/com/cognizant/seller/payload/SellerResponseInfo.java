package com.cognizant.seller.payload;

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
public class SellerResponseInfo {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String pin;
	private String phone;
	private String email;
}

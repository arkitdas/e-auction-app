package com.cognizant.product.payload;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductAddRequestInfo {
	
	@NotNull(message = "Seller Information cannot be empty")
	UserRequestInfo seller;
	
	@NotNull(message = "Product Information cannot be empty")
	ProductInfo product;
	
}

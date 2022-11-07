package com.cognizant.product.payload;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cognizant.product.meta.Category;
import com.cognizant.product.validation.ValidEnum;

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
public class ProductInfo {

	
	@NotNull( message = "Product Name cannot be null")
	@NotBlank( message = "Product Name cannot be blank")
	@Size(min = 5, message = "Product Name cannot be less than 5 characters")
	@Size(max = 30, message = "Product Name cannot be greater than 30 characters")
	private String productName;
	
	private String shortDescription;
	
	private String detailedDescription;
	
	@ValidEnum(message = "Invalid item condition", isRequired = false, enumClass = Category.class)
	private String categopry;
	
	@NotNull( message = "Starting Price cannot be null")
	private Double startingPrice;
	
	@NotNull( message = "Bid End Date cannot be null")
	private Date bidEndDate;
	
	@NotNull( message = "Seller Details cannot be null")
	private String sellerId;
}

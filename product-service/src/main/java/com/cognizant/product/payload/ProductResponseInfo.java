package com.cognizant.product.payload;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cognizant.product.meta.Category;
import com.cognizant.product.model.Product;
import com.cognizant.product.validation.ValidEnum;

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
public class ProductResponseInfo {

	private String productId;
	
	private String productName;
	
	private String shortDescription;
	
	private String detailedDescription;
	
	private String categopry;
	
	private Double startingPrice;
	
	private Date bidEndDate;
	
	private String sellerId;
}

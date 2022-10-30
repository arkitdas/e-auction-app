package com.cognizant.seller.payload;

import java.util.Date;

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
public class ProductShortResponseInfo {
	private String shortDescription;
	private String detailedDescription;
	private String categopry;
	private Double startingPrice;
	private Date bidEndDate;
}

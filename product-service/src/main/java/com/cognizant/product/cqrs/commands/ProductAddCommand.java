package com.cognizant.product.cqrs.commands;

import java.util.Date;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class ProductAddCommand extends BaseCommand {

	private String shortDescription;

	private String detailedDescription;

	private String categopry;

	private Double startingPrice;

	private Date bidEndDate;

	private String sellerId;
}

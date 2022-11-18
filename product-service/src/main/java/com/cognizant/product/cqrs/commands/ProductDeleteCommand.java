package com.cognizant.product.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDeleteCommand extends BaseCommand {

	private String productId;
	
	private String sellerId;


}

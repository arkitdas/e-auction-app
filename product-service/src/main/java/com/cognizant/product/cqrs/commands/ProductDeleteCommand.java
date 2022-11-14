package com.cognizant.product.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data	
public class ProductDeleteCommand extends BaseCommand {

	private String productId;


}

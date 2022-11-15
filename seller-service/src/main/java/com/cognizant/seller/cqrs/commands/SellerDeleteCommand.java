package com.cognizant.seller.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerDeleteCommand extends BaseCommand {

	private String productId;


}

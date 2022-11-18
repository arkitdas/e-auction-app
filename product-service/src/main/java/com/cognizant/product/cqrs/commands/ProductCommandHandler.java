package com.cognizant.product.cqrs.commands;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.handlers.EventSourcingHandler;
import com.cognizant.product.client.BidClient;
import com.cognizant.product.cqrs.aggreagate.ProductAggregate;
import com.cognizant.product.model.Product;
import com.cognizant.product.payload.BidResponseInfo;
import com.cognizant.product.repository.ProductRepository;

@Service
public class ProductCommandHandler implements CommandHandler{

	private EventSourcingHandler<ProductAggregate> eventSourcingHandler;
	private ProductRepository productRepository;
	private BidClient bidClient;
	
	ProductCommandHandler(EventSourcingHandler<ProductAggregate> eventSourcingHandler,
			ProductRepository productRepository, BidClient bidClient) {
		this.eventSourcingHandler = eventSourcingHandler;
		this.productRepository = productRepository;
		this.bidClient = bidClient;
	}

	@Override
	public void handle(ProductAddCommand command) {
		var aggregate = new ProductAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(ProductDeleteCommand command) {
		
		Optional<Product> productOp = productRepository.findByProductIdAndCreatedByAndActive(command.getProductId(), command.getSellerId(), true);
		if(productOp.isEmpty()) {
			throw new RuntimeException("No product found with product id "+command.getProductId());
		}
		
		Product product = productOp.get();
		
		if(new Date().after(product.getBidEndDate())) {
			throw new RuntimeException("Product cannot be deleted after the bid end date");
		}
		
		List<BidResponseInfo> response = bidClient.getAllBidsForProducts(command.getProductId());
		if(!Objects.isNull(response) && !response.isEmpty() && response.size()>0) {
			throw new RuntimeException("Product cannot be deleted as it contains "+response.size()+" no of bids");
		}
		var aggregate = eventSourcingHandler.getById(command.getProductId());
        aggregate.delete();
        eventSourcingHandler.save(aggregate);
	}

}

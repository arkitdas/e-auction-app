package com.cognizant.bid.cqrs.commands;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cognizant.bid.client.ProductClient;
import com.cognizant.bid.client.UserClient;
import com.cognizant.bid.cqrs.aggreagate.BidAggregate;
import com.cognizant.bid.exception.BuyerNotFoundException;
import com.cognizant.bid.exception.InvalidOperationException;
import com.cognizant.bid.exception.ProductNotFoundException;
import com.cognizant.bid.meta.UserType;
import com.cognizant.bid.model.BidDetails;
import com.cognizant.bid.payload.ProductResponseInfo;
import com.cognizant.bid.payload.UserResponseInfo;
import com.cognizant.bid.repository.BidDetailsRepository;
import com.cognizant.cqrs.core.handlers.EventSourcingHandler;

@Service
public class BidCommandHandler implements CommandHandler{

	private EventSourcingHandler<BidAggregate> eventSourcingHandler;
	private ProductClient productClient;
	private UserClient userClient;
	private BidDetailsRepository bidDetailsRepository;
	
	BidCommandHandler(EventSourcingHandler<BidAggregate> eventSourcingHandler,
			ProductClient productClient, BidDetailsRepository bidDetailsRepository, UserClient userClient) {
		this.eventSourcingHandler = eventSourcingHandler;
		this.productClient = productClient;
		this.bidDetailsRepository = bidDetailsRepository;
		this.userClient = userClient;
	}

	@Override
	public void handle(BidAddCommand command) {
		
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(command.getProductId());
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+command.getProductId());
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		} else if(command.getBidAmount() < productResponseInfo.getStartingPrice()) {
			throw new InvalidOperationException("Invalid bid amount, amount less than starting price");
		}
		
		UserResponseInfo user = null;
		
		try {
			user = userClient.getUserByEmailId(command.getBuyer().getEmail());
		}catch(Exception e) {
			//TODO place log "No user found with email id"+event.getBuyer().getEmail()
		}
		
		if(!Objects.isNull(user)) {
			Optional<List<BidDetails>> bidDetailsOp = bidDetailsRepository.findByBuyerId(user.getUserId());
			if(bidDetailsOp.isPresent() && bidDetailsOp.get().stream()
					.anyMatch((bidDetails -> bidDetails.getProductId().equalsIgnoreCase(command.getProductId()))) ) {
				throw new InvalidOperationException("Invalid bid, Bid already present with same product");
			}
		}
		
		var aggregate = new BidAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(BidUpdateAmountCommand command) {
		
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(command.getProductId());
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+command.getProductId());
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		
		UserResponseInfo buyer = userClient.getUserByEmailId(command.getEmail());
		
		if(!UserType.Buyer.toString().equals(buyer.getUserType())) {
			throw new InvalidOperationException("Only buyer can update bid amount");
		}
		
		
		if(Objects.isNull(buyer)) {
			throw new BuyerNotFoundException("No buyer found with email id :"+command.getEmail());
		}

		Optional<BidDetails> bidDetailOp = bidDetailsRepository.findByProductIdAndBuyerId(command.getProductId(), buyer.getUserId());
		
		if(Objects.isNull(bidDetailOp.isEmpty())) {
			throw new InvalidOperationException("No bid present for product id: "+command.getProductId());
		} else if(!bidDetailOp.get().getBuyerId().equalsIgnoreCase(buyer.getUserId())) {
			throw new InvalidOperationException("Invalid bid details provided");
		}else if(bidDetailOp.get().getBidAmount()  < productResponseInfo.getStartingPrice()) {
			throw new InvalidOperationException("Invalid bid amount, amount less than starting price");
		}
		
		var aggregate = eventSourcingHandler.getById(bidDetailOp.get().getBidId());
        aggregate.updateBidAmount(command.getProductId(), command.getBidAmount(), command.getEmail());
        eventSourcingHandler.save(aggregate);
	}

}

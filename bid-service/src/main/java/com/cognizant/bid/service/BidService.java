package com.cognizant.bid.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cognizant.bid.client.ProductClient;
import com.cognizant.bid.client.UserClient;
import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.cqrs.events.BidUpdateAmountdEvent;
import com.cognizant.bid.exception.BuyerNotFoundException;
import com.cognizant.bid.exception.InvalidOperationException;
import com.cognizant.bid.exception.ProductNotFoundException;
import com.cognizant.bid.mapper.BidMapper;
import com.cognizant.bid.meta.UserType;
import com.cognizant.bid.model.BidDetails;
import com.cognizant.bid.payload.BidResponseInfo;
import com.cognizant.bid.payload.ProductResponseInfo;
import com.cognizant.bid.payload.UserResponseInfo;
import com.cognizant.bid.repository.BidDetailsRepository;

@Service
public class BidService {
	
	private BidMapper bidMapper;
	private ProductClient productClient;
	private UserClient userClient;
	private BidDetailsRepository bidDetailsRepository;
	
	BidService(BidMapper bidMapper, ProductClient productClient,
			BidDetailsRepository bidDetailsRepository, UserClient userClient) {
		this.bidMapper = bidMapper;
		this.productClient = productClient;
		this.bidDetailsRepository = bidDetailsRepository;
		this.userClient = userClient;
	}
	
	@Transactional
	public BidResponseInfo placeBids(BidAddEvent event) throws Exception {
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(event.getProductId());
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+event.getProductId());
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		
		UserResponseInfo user = null;
		
		try {
			user = userClient.getUserByEmailId(event.getBuyer().getEmail());
		}catch(Exception e) {
			//TODO place log "No user found with email id"+event.getBuyer().getEmail()
		}
		
		user = userClient.addUser(event.getBuyer());
		
		Optional<List<BidDetails>> bidDetailsOp = bidDetailsRepository.findByBuyerId(user.getUserId());
		if(bidDetailsOp.isPresent() && bidDetailsOp.get().stream()
				.anyMatch((bidDetails -> bidDetails.getProductId().equalsIgnoreCase(event.getProductId()))) ) {
			throw new InvalidOperationException("Invalid bid, Bid already present with same product");
		} else if(event.getBidAmount() < productResponseInfo.getStartingPrice()) {
			throw new InvalidOperationException("Invalid bid amount, amount less than starting price");
		}
		
		BidDetails bidDetails = bidMapper.toBidDetails(event);
		bidDetails.setBuyerId(user.getUserId());
		bidDetails.setCreatedDate(new Date());
		bidDetails.setLastModifiedDate(new Date());
		bidDetails.setCreatedBy(user.getEmail());
		bidDetails = bidDetailsRepository.save(bidDetails);
		
		BidResponseInfo bidResponseInfo = bidMapper.toBidResponseInfo(bidDetails);
		bidResponseInfo.setBuyer(user);
		
		return bidResponseInfo;
	}
	
	@Transactional
	public BidResponseInfo updateBidAmount(BidUpdateAmountdEvent event) throws Exception {
		
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(event.getProductId());
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+event.getProductId());
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		
		UserResponseInfo buyer = userClient.getUserByEmailId(event.getEmail());
		
		if(!UserType.Buyer.toString().equals(buyer.getUserType())) {
			throw new InvalidOperationException("Only buyer can update bid amount");
		}
		
		
		if(Objects.isNull(buyer)) {
			throw new BuyerNotFoundException("No buyer found with email id :"+event.getEmail());
		}

		BidDetails bidDetail = bidDetailsRepository.getById(event.getBidId());
		
		if(Objects.isNull(bidDetail)) {
			throw new InvalidOperationException("Amount cannot be updated for bid id: "+event.getBidId());
		} else if(bidDetail.getBuyerId().equalsIgnoreCase(buyer.getUserId())) {
			throw new InvalidOperationException("Invalid bid details provided");
		}else if(event.getBidAmount()  < productResponseInfo.getStartingPrice()) {
			throw new InvalidOperationException("Invalid bid amount, amount less than starting price");
		}
		
		bidDetail.setBidAmount(event.getBidAmount());
		bidDetail = bidDetailsRepository.save(bidDetail);
		
		return BidResponseInfo.builder().productId(bidDetail.getProductId())
				.bidId(bidDetail.getBidId())
				.bidAmount(bidDetail.getBidAmount())
				.buyer(buyer).build();
	}
	
	@Transactional
	public List<BidResponseInfo> getAllBidsDetailsByProduct(String productId) throws InvalidOperationException {
		Optional<List<BidDetails>> bidDetailsOp = bidDetailsRepository.findByProductId(productId);
		if(bidDetailsOp.isEmpty()) {
			throw new InvalidOperationException("No bids found for product id : "+productId);
		}
		List<BidDetails> bidDetails = bidDetailsOp.get();
		
		List<BidResponseInfo> bidResponseInfos = bidDetails.stream()
				.map(bidDetail -> {
					return BidResponseInfo.builder()
							.productId(bidDetail.getProductId())
							.bidId(bidDetail.getBidId())
							.bidAmount(bidDetail.getBidAmount())
							.buyer(userClient.getUserByUserId(bidDetail.getBuyerId()))
							.build();
				}).collect(Collectors.toList());
		
		return bidResponseInfos;
	}

}

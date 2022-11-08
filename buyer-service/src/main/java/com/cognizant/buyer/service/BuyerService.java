package com.cognizant.buyer.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cognizant.buyer.client.ProductClient;
import com.cognizant.buyer.exception.BuyerNotFoundException;
import com.cognizant.buyer.exception.InvalidOperationException;
import com.cognizant.buyer.exception.ProductNotFoundException;
import com.cognizant.buyer.mapper.BuyerMapper;
import com.cognizant.buyer.model.BidDetails;
import com.cognizant.buyer.model.Buyer;
import com.cognizant.buyer.payload.BidDetailResponseInfo;
import com.cognizant.buyer.payload.BidRequestInfo;
import com.cognizant.buyer.payload.BidResponseInfo;
import com.cognizant.buyer.payload.ProductResponseInfo;
import com.cognizant.buyer.repository.BidDetailsRepository;
import com.cognizant.buyer.repository.BuyerRepository;

@Service
public class BuyerService {
	
	private BuyerRepository buyerRepository;
	private BuyerMapper buyerMapper;
	private ProductClient productClient;
	private BidDetailsRepository bidDetailsRepository;
	
	BuyerService(BuyerRepository buyerRepository, BuyerMapper buyerMapper, ProductClient productClient,
			BidDetailsRepository bidDetailsRepository) {
		this.buyerRepository = buyerRepository;
		this.buyerMapper = buyerMapper;
		this.productClient = productClient;
		this.bidDetailsRepository = bidDetailsRepository;
	}
	
	public BidResponseInfo placeBids(BidRequestInfo bidRequestInfo) throws Exception {
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(bidRequestInfo.getBidDetails().getProductId());
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+bidRequestInfo.getBidDetails().getProductId());
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		
		Optional<Buyer> buerOp = buyerRepository.findByEmail(bidRequestInfo.getBuyer().getEmail());
		
		Buyer buyer = null;
		if(buerOp.isEmpty()) {
			buyer = buyerMapper.toBuyer(bidRequestInfo.getBuyer());
			buyer.setBuyerId(UUID.randomUUID().toString());
			buyer.setCreatedDate(new Date());
			buyer.setLastModifiedDate(new Date());
			buyer = buyerRepository.save(buyer);
		}else {
			buyer = buerOp.get();
		}
		
		
		if(buyer.getBidDetails().stream()
				.anyMatch((bidDetails -> bidDetails.getProductId().equalsIgnoreCase(bidRequestInfo.getBidDetails().getProductId()))) ) {
			throw new InvalidOperationException("Invalid bid, Bid already present with same product");
		}
		
		BidDetails bidDetails = buyerMapper.toBidDetails(bidRequestInfo.getBidDetails());
		bidDetails.setBidId(UUID.randomUUID().toString());
		bidDetails.setBuyer(buyer);
		bidDetails.setCreatedDate(new Date());
		bidDetails.setLastModifiedDate(new Date());
		bidDetails.setCreatedBy(buyer.getEmail());
		bidDetails = bidDetailsRepository.save(bidDetails);
		
		BidResponseInfo bidResponseInfo = buyerMapper.toBidResponseInfo(buyer);
		bidResponseInfo.setBidDetails(Set.of(buyerMapper.toBidDetailsResponseInfo(bidDetails)));
		
		return bidResponseInfo;
	}
	
	public BidResponseInfo updateBidAmount(String productId, String buyerEmailId, double newBidAmount) throws Exception {
		
		Optional<Buyer> buerOp = buyerRepository.findByEmail(buyerEmailId);
		
		if(buerOp.isEmpty()) {
			throw new BuyerNotFoundException("No buyer found with email id :"+buyerEmailId);
		}
		Buyer buyer = buerOp.get();
		BidResponseInfo bidResponseInfo = buyerMapper.toBidResponseInfo(buyer);
		
		BidDetails bidDetails = buyer.getBidDetails().stream()
				.filter(bidDetail -> bidDetail.getProductId().equalsIgnoreCase(productId)).findFirst().get();
		
		bidDetails.setBidAmount(newBidAmount);
		bidDetails = bidDetailsRepository.save(bidDetails);
		
		bidResponseInfo.setBidDetails(Set.of(buyerMapper.toBidDetailsResponseInfo(bidDetails)));
		
		return bidResponseInfo;
	}
	
	public List<BidDetailResponseInfo> getAllBidsDetailsByProduct(String productId) throws InvalidOperationException {
		Optional<List<BidDetails>> bidDetailsOp = bidDetailsRepository.findByProductId(productId);
		if(bidDetailsOp.isEmpty()) {
			throw new InvalidOperationException("No bids found for product id : "+productId);
		}
		List<BidDetails> bidDetails = bidDetailsOp.get();
		
		List<BidDetailResponseInfo> bidDetailResponseInfos = bidDetails.stream()
				.map(bidDetail -> buyerMapper.toBidDetailResponseInfo(bidDetail)).collect(Collectors.toList());
		
		return bidDetailResponseInfos;
	}

}

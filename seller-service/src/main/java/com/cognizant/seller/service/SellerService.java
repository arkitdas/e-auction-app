package com.cognizant.seller.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cognizant.seller.client.BuyerClient;
import com.cognizant.seller.client.ProductClient;
import com.cognizant.seller.exception.InvalidOperationException;
import com.cognizant.seller.exception.ProductNotFoundException;
import com.cognizant.seller.mapper.SellerMapper;
import com.cognizant.seller.model.Seller;
import com.cognizant.seller.payload.BidDetailResponseInfo;
import com.cognizant.seller.payload.BidDetailsResponseInfo;
import com.cognizant.seller.payload.ProductAddRequestInfo;
import com.cognizant.seller.payload.ProductAddResponsenfo;
import com.cognizant.seller.payload.ProductInfo;
import com.cognizant.seller.payload.ProductResponseInfo;
import com.cognizant.seller.payload.ProductShortResponseInfo;
import com.cognizant.seller.repository.SellerRepository;

@Service
public class SellerService {
	
	private SellerRepository sellerRepository;
	private BuyerClient buyerClient;
	private ProductClient productClient;
	private SellerMapper sellerMapper;
	
	SellerService(SellerRepository sellerRepository, BuyerClient buyerClient,ProductClient productClient, SellerMapper sellerMapper){
		this.sellerRepository = sellerRepository;
		this.buyerClient = buyerClient;
		this.productClient = productClient;
		this.sellerMapper = sellerMapper;
	}
	
	@Transactional
	public ProductAddResponsenfo addProduct(ProductAddRequestInfo productAddRequestInfo) {
		
		Seller seller = sellerMapper.toSeller(productAddRequestInfo.getSeller());
		Optional<Seller> sellerOp = sellerRepository.findByEmail(seller.getEmail());
		if(sellerOp.isEmpty()) {
			seller.setSellerId(UUID.randomUUID().toString());
			seller.setCreatedDate(new Date());
			seller.setLastModifiedDate(new Date());
			sellerRepository.save(seller);
		}else {
			seller = sellerOp.get();
		}
		
		
		ProductInfo productInfo = productAddRequestInfo.getProduct();
		productInfo.setSellerId(seller.getSellerId());
		ProductResponseInfo productResponseInfo = productClient.addProduct(productInfo);
		
		
		return ProductAddResponsenfo.builder().product(productResponseInfo)
				.seller(sellerMapper.toSellerResponseInfo(seller)).build();
		
	}
	
	public List<BidDetailsResponseInfo> getAllBidDetailsByProductId(String productId) throws ProductNotFoundException, InvalidOperationException {
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(productId);
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+productId);
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		ProductShortResponseInfo productShortResponseInfo = sellerMapper.toProductShortResponseInfo(productResponseInfo);
		List<BidDetailResponseInfo> bidDetails =  buyerClient.getAllBidDetailsByProductId(productId);
		List<BidDetailsResponseInfo> bidDetailsResponseInfos = bidDetails.stream().map(bidDetail -> sellerMapper.toBidDetailsResponseInfo(bidDetail)).collect(Collectors.toList());
		bidDetailsResponseInfos.stream().forEach(bidResponseInfo -> bidResponseInfo.setProduct(productShortResponseInfo));
		
		return bidDetailsResponseInfos;
	}
	
	public boolean deleteProduct(String productId) throws ProductNotFoundException {
		return productClient.deleteProductByProductId(productId);
	}
}

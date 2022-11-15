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
import com.cognizant.seller.cqrs.events.SellerAddEvent;
import com.cognizant.seller.exception.InvalidOperationException;
import com.cognizant.seller.exception.ProductNotFoundException;
import com.cognizant.seller.exception.SellerNotFoundException;
import com.cognizant.seller.mapper.SellerMapper;
import com.cognizant.seller.model.Seller;
import com.cognizant.seller.payload.BidDetailResponseInfo;
import com.cognizant.seller.payload.BidDetailsResponseInfo;
import com.cognizant.seller.payload.ProductAddRequestInfo;
import com.cognizant.seller.payload.ProductAddResponsenfo;
import com.cognizant.seller.payload.ProductInfo;
import com.cognizant.seller.payload.ProductResponseInfo;
import com.cognizant.seller.payload.ProductShortResponseInfo;
import com.cognizant.seller.payload.SellerProductResponseInfo;
import com.cognizant.seller.payload.SellerResponseInfo;
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
	public ProductAddResponsenfo addProduct(SellerAddEvent sellerAddEvent) {
		Seller seller = sellerMapper.toSeller(sellerAddEvent);
		Optional<Seller> sellerOp = sellerRepository.findByEmail(seller.getEmail());
		if(sellerOp.isEmpty()) {
			seller.setCreatedDate(new Date());
			seller.setLastModifiedDate(new Date());
			sellerRepository.save(seller);
		}else {
			seller = sellerOp.get();
		}
		
		ProductInfo productInfo = sellerAddEvent.getProduct();
		productInfo.setSellerId(seller.getSellerId());
		ProductResponseInfo productResponseInfo = productClient.addProduct(productInfo);
		
		
		return ProductAddResponsenfo.builder().product(productResponseInfo)
				.seller(sellerMapper.toSellerResponseInfo(seller)).build();
	}
	
	@Deprecated
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
	
	public ProductAddResponsenfo showAllSellerProductBySellerEmailId(String productId) throws ProductNotFoundException, InvalidOperationException {
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(productId);
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+productId);
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		
		Optional<Seller> sellerOp = sellerRepository.findById(productResponseInfo.getSellerId());
		return ProductAddResponsenfo.builder().product(productResponseInfo)
				.seller(sellerMapper.toSellerResponseInfo(sellerOp.get())).build();
		
	}
	
	public ProductAddResponsenfo getProductDetailsByProductId(String productId) throws ProductNotFoundException, InvalidOperationException {
		ProductResponseInfo productResponseInfo = productClient.getProductsByProductId(productId);
		if(Objects.isNull(productResponseInfo)) {
			throw new ProductNotFoundException("No product found with product id :"+productId);
		} else if (new Date().after(productResponseInfo.getBidEndDate())) {
			throw new InvalidOperationException("Bid cannot be placed as bid date has expired");
		}
		
		Optional<Seller> sellerOp = sellerRepository.findById(productResponseInfo.getSellerId());
		return ProductAddResponsenfo.builder().product(productResponseInfo)
				.seller(sellerMapper.toSellerResponseInfo(sellerOp.get())).build();
		
	}
	
	public SellerProductResponseInfo getAllSellerProductBySellerEmailId(String emailId) throws ProductNotFoundException, InvalidOperationException, SellerNotFoundException {
		Optional<Seller> sellerOp = sellerRepository.findByEmail(emailId);
		if(sellerOp.isEmpty()) {
			throw new SellerNotFoundException("No seller found with email id :"+emailId);
		}
		
		List<ProductResponseInfo> productResponseInfos = productClient.getProductsBySellerId(sellerOp.get().getSellerId());
		if(Objects.isNull(productResponseInfos)) {
			throw new ProductNotFoundException("No product found for seller");
		}
		
		SellerProductResponseInfo responseInfo = sellerMapper.toSellerProductResponseInfo(sellerOp.get());
		responseInfo.setProducts(productResponseInfos);
		return responseInfo;
		
	}
	
	public SellerProductResponseInfo getAllSellerProductBySellerId(String sellerId) throws SellerNotFoundException, ProductNotFoundException, InvalidOperationException {
		Optional<Seller> sellerOp = sellerRepository.findById(sellerId);
		if(sellerOp.isEmpty()) {
			throw new SellerNotFoundException("No seller found with seller id :"+sellerId);
		}
		
		List<ProductResponseInfo> productResponseInfos = productClient.getProductsBySellerId(sellerOp.get().getSellerId());
		if(Objects.isNull(productResponseInfos)) {
			throw new ProductNotFoundException("No product found for seller");
		}
		
		SellerProductResponseInfo responseInfo = sellerMapper.toSellerProductResponseInfo(sellerOp.get());
		responseInfo.setProducts(productResponseInfos);
		return responseInfo;
		
	}
	
	public SellerResponseInfo getSellerDetailsByEmailId(String email) throws SellerNotFoundException {
		Optional<Seller> sellerOp = sellerRepository.findByEmail(email);
		if(sellerOp.isEmpty()) {
			throw new SellerNotFoundException("No seller found with email id :"+email);
		}
		return sellerMapper.toSellerResponseInfo(sellerOp.get());
	}
}

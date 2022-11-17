package com.cognizant.product.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cognizant.product.client.UserClient;
import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.exception.InvalidOperationException;
import com.cognizant.product.exception.ProductNotFoundException;
import com.cognizant.product.mapper.ProductMapper;
import com.cognizant.product.model.Product;
import com.cognizant.product.payload.ProductInfo;
import com.cognizant.product.payload.ProductResponseInfo;
import com.cognizant.product.payload.UserResponseInfo;
import com.cognizant.product.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	private ProductMapper productMapper;
	private UserClient userClient;
	
	ProductService(ProductRepository productRepository, ProductMapper productMapper, UserClient userClient){
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.userClient = userClient;
	}

	@Deprecated
	public ProductResponseInfo addProduct(ProductInfo productInfo) {
		Product product = productMapper.toProduct(productInfo);
		product.setProductId(UUID.randomUUID().toString());
		product.setCreatedDate(new Date());
		product.setLastModifiedDate(new Date());
		product.setActive(true);
		product.setCreatedBy(productInfo.getSellerId());
		product.setLastModifiedBy(productInfo.getSellerId());
		product = productRepository.save(product);
		return productMapper.toProductResponseInfo(product);
	}
	
	public ProductResponseInfo addProduct(ProductAddEvent productAddEvent) {
		
		UserResponseInfo seller = userClient.addUser(productAddEvent.getSeller());
		
		Product product = productMapper.toProduct(productAddEvent);
		product.setCreatedDate(new Date());
		product.setLastModifiedDate(new Date());
		product.setActive(true);
		product.setCreatedBy(seller.getUserIdId());
		product.setLastModifiedBy(seller.getUserIdId());
		product = productRepository.save(product);
		return productMapper.toProductResponseInfo(product);
	}
	
	public boolean deleteProduct(String productId) throws ProductNotFoundException {
		Optional<Product> productOp = productRepository.findById(productId);
		if(productOp.isEmpty()) {
			throw new ProductNotFoundException("No product found with product id "+productId);
		}
		
		Product product = productOp.get();
		product.setActive(false);
		
		productRepository.save(product);
		return true;
		
	}
	
	public boolean deleteProduct(String productId, String sellerId) throws ProductNotFoundException, InvalidOperationException {
		Optional<Product> productOp = productRepository.findByProductIdAndCreatedByAndActive(productId, sellerId, true);
		if(productOp.isEmpty()) {
			throw new ProductNotFoundException("No product found with product id "+productId);
		}
		
		Product product = productOp.get();
		
		if(new Date().after(product.getBidEndDate())) {
			throw new InvalidOperationException("Product cannot be deleted after the bid end date");
		}
		product.setActive(false);
		
		productRepository.save(product);
		return true;
		
	}
	
	public List<ProductResponseInfo> getAllProductBySeller(String sellerId) throws ProductNotFoundException {
		Optional<List<Product>> productsOp = productRepository.findByCreatedByAndActive(sellerId, true);
		if(productsOp.isEmpty()) {
			throw new ProductNotFoundException("No product found for seller id: "+sellerId);
		}
		return productsOp.get().stream().map(product -> productMapper.toProductResponseInfo(product)).collect(Collectors.toList());
	}
	
	public ProductResponseInfo getProductByProductIdAndSeller(String productId,String sellerId) throws ProductNotFoundException {
		Optional<Product> productOp = productRepository.findByProductIdAndCreatedByAndActive(productId, sellerId, true);
		if(productOp.isEmpty()) {
			throw new ProductNotFoundException("No product found with product id "+productId);
		}
		return productMapper.toProductResponseInfo(productOp.get());
	}
	
	public ProductResponseInfo getProductByProductId(String productId) throws ProductNotFoundException {
		Optional<Product> productOp = productRepository.findByProductIdAndActive(productId, true);
		if(productOp.isEmpty()) {
			throw new ProductNotFoundException("No product found with product id "+productId);
		}
		return productMapper.toProductResponseInfo(productOp.get());
	}
	
	public List<ProductResponseInfo> getAllProduct() throws ProductNotFoundException {
		Optional<List<Product>> productsOp = productRepository.findByActive(true);
		if(productsOp.isEmpty()) {
			throw new ProductNotFoundException("No product found ");
		}
		return productsOp.get().stream().map(product -> productMapper.toProductResponseInfo(product)).collect(Collectors.toList());
	}
}

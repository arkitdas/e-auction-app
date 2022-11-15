package com.cognizant.seller.client;

import java.util.List;
import java.util.Objects;

import javax.management.openmbean.InvalidOpenTypeException;

import org.springframework.stereotype.Component;

import com.cognizant.seller.exception.ProductNotFoundException;
import com.cognizant.seller.feign.ProductsFeignClient;
import com.cognizant.seller.payload.ApiResponse;
import com.cognizant.seller.payload.ProductInfo;
import com.cognizant.seller.payload.ProductResponseInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das 
 * Client utility class for communicating with product-service
 */
@Component
@Slf4j
public class ProductClient {

	private ProductsFeignClient productsFeignClient;
	
	ProductClient(ProductsFeignClient productsFeignClient){
		this.productsFeignClient = productsFeignClient;
	}
	
	public ProductResponseInfo addProduct(ProductInfo productInfo) {
		ApiResponse<ProductResponseInfo> response = productsFeignClient.addProduct(productInfo).getBody();;
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (ProductResponseInfo) response.getPayload();
        }else{
            log.error("Failed to add product");
            return null;
        }
	}
	
	public ProductResponseInfo getProductsByProductId(String productId) {
		ApiResponse<ProductResponseInfo> response = productsFeignClient.getProduct(productId).getBody();;
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (ProductResponseInfo) response.getPayload();
        }else{
            log.error("Failed to credit transaction");
            return null;
        }
	}
	
	public boolean deleteProductByProductId(String productId) throws ProductNotFoundException{
		ApiResponse<Boolean> response = productsFeignClient.deleteProduct(productId).getBody();;
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (boolean) response.getPayload();
        }else{
            log.error("Failed to delete product with product id :"+productId);
            throw new InvalidOpenTypeException("Failed to delete product with product id :"+productId);
        }
	}
	
	public List<ProductResponseInfo> getProductsBySellerId(String sellerId) {
		ApiResponse<List<ProductResponseInfo>> response = productsFeignClient.getAllProductBySeller(sellerId).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (List<ProductResponseInfo>) response.getPayload();
        }else{
            log.error("Failed to credit transaction");
            return null;
        }
	}
	
}

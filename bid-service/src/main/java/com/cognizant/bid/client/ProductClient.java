package com.cognizant.bid.client;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cognizant.bid.feign.ProductsFeignClient;
import com.cognizant.bid.payload.ApiResponse;
import com.cognizant.bid.payload.ProductResponseInfo;

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
	
}

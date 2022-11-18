package com.cognizant.product.client;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cognizant.product.feign.BidFeignClient;
import com.cognizant.product.payload.ApiResponse;
import com.cognizant.product.payload.BidResponseInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das 
 * Client utility class for communicating with product-service
 */
@Component
@Slf4j
public class BidClient {

	private BidFeignClient bidFeignClient;
	
	BidClient(BidFeignClient bidFeignClient){
		this.bidFeignClient = bidFeignClient;
	}
	
	
	public List<BidResponseInfo> getAllBidsForProducts(String productId) {
		ApiResponse<List<BidResponseInfo>> response = bidFeignClient.getAllBidsForProducts(productId, null, null).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (List<BidResponseInfo>) response.getPayload();
        }else{
            log.error("Failed to get bid details for product "+productId);
            return null;
        }
	}
	
	
}

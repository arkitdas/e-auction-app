package com.cognizant.seller.client;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cognizant.seller.feign.BuyerFeignClient;
import com.cognizant.seller.payload.ApiResponse;
import com.cognizant.seller.payload.BidDetailResponseInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BuyerClient {

	
	private BuyerFeignClient buyerFeignClient;
	
	BuyerClient(BuyerFeignClient buyerFeignClient){
		this.buyerFeignClient = buyerFeignClient;
	}
	
	public List<BidDetailResponseInfo> getAllBidDetailsByProductId(String productId) {
		ApiResponse<List<BidDetailResponseInfo>> response = buyerFeignClient.getAllBidsForProducts(productId).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (List<BidDetailResponseInfo>) response.getPayload();
        }else{
            log.error("Failed to get bid details by product id");
            return null;
        }
	}
}

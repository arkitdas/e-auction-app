package com.cognizant.seller.feign;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognizant.seller.payload.ApiResponse;
import com.cognizant.seller.payload.BidDetailResponseInfo;

@FeignClient(url = "${seller.remote.services.buyer.url:#{null}}", name = "${seller.remote.services.buyer.name}", path = "/v1/buyer")
public interface BuyerFeignClient {

	@GetMapping("/show-bids/{productId}")
	public ResponseEntity<ApiResponse<List<BidDetailResponseInfo>>> getAllBidsForProducts(@NotBlank(message = "productId") @PathVariable String productId);
}

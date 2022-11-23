package com.cognizant.product.feign;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cognizant.product.payload.ApiResponse;
import com.cognizant.product.payload.BidResponseInfo;

@FeignClient(name = "${product.remote.services.bid.name}", path = "/bid-service/v1")
public interface BidFeignClient {

	@GetMapping("/seller/show-bids/{productId}")
	public ResponseEntity<ApiResponse<List<BidResponseInfo>>> getAllBidsForProducts(
			@NotBlank(message = "Product Id cannot be blank") @PathVariable(value = "productId") String productId,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size);

}

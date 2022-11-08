package com.cognizant.buyer.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.buyer.exception.InvalidOperationException;
import com.cognizant.buyer.payload.ApiResponse;
import com.cognizant.buyer.payload.BidRequestInfo;
import com.cognizant.buyer.service.BuyerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das
 */
@Slf4j
@RestController
@RequestMapping("/v1/buyer")
public class BuyerController {
	
	private BuyerService buyerService;
	
	BuyerController(BuyerService buyerService) {
		this.buyerService = buyerService;
	}
	
	@PostMapping("/place-bid")
	public ResponseEntity<?> placeBids(@RequestBody @Valid BidRequestInfo bidRequestInfo) throws Exception {
		log.debug("bidRequestInfo [" + bidRequestInfo + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, buyerService.placeBids(bidRequestInfo)), HttpStatus.OK);
	}
	
	@PutMapping("/update-bid/{productId}/{buyerEmailId}/newBidAmount")
	public ResponseEntity<?> updateBidAmount(@NotBlank(message = "productId") @PathVariable String productId,
			@NotBlank(message = "buyerEmailId") @PathVariable String buyerEmailId,
			@NotBlank(message = "newBidAmount") @PathVariable double newBidAmount) throws Exception {
		
		log.debug("productId [" + productId + "] , buyerEmailId ["+buyerEmailId+"] , newBidAmount ["+newBidAmount+"]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, buyerService.updateBidAmount(productId, buyerEmailId, newBidAmount)), HttpStatus.OK);
	}
	
	@GetMapping("/show-bids/{productId}")
	public ResponseEntity<?> getAllBidsForProducts(@NotBlank(message = "productId") @PathVariable String productId) throws InvalidOperationException  {
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, buyerService.getAllBidsDetailsByProduct(productId)), HttpStatus.OK);
	}

}

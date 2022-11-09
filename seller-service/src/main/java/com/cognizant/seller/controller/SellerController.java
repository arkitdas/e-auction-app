package com.cognizant.seller.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.seller.exception.InvalidOperationException;
import com.cognizant.seller.exception.ProductNotFoundException;
import com.cognizant.seller.payload.ApiResponse;
import com.cognizant.seller.payload.ProductAddRequestInfo;
import com.cognizant.seller.service.SellerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das
 */
@Slf4j
@RestController
@RequestMapping("/v1/seller")
public class SellerController {
	
	private SellerService sellerService;
	
	SellerController(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<?> addProduct(@RequestBody @Valid ProductAddRequestInfo productAddRequestInfo) throws Exception {
		log.debug("productAddRequestInfo [" + productAddRequestInfo + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, sellerService.addProduct(productAddRequestInfo)), HttpStatus.OK);
	}
	
	@GetMapping("/show-bids/{productId}")
	public ResponseEntity<?> showBidsByProduct(@NotBlank(message = "productId") @PathVariable String productId)
			throws ProductNotFoundException, InvalidOperationException {
		log.debug("showBidsByProduct  >>");
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, sellerService.getAllBidDetailsByProductId(productId)),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@NotBlank(message = "productId") @PathVariable String productId) throws ProductNotFoundException {
		log.debug("deleteProduct  >>");
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, sellerService.deleteProduct(productId)), HttpStatus.OK);
	}
	

}

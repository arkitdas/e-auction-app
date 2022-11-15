package com.cognizant.seller.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognizant.cqrs.core.infrastructure.CommandDispatcher;
import com.cognizant.seller.cqrs.commands.SellerAddCommand;
import com.cognizant.seller.exception.InvalidOperationException;
import com.cognizant.seller.exception.ProductNotFoundException;
import com.cognizant.seller.exception.SellerNotFoundException;
import com.cognizant.seller.mapper.SellerMapper;
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
	private CommandDispatcher commandDispatcher;
	private SellerMapper sellerMapper;
	
	SellerController(SellerService sellerService, CommandDispatcher commandDispatcher, SellerMapper sellerMapper) {
		this.sellerService = sellerService;
		this.commandDispatcher = commandDispatcher;
		this.sellerMapper = sellerMapper;
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<?> addProduct(@RequestBody @Valid ProductAddRequestInfo productAddRequestInfo) throws Exception {
		log.debug("productAddRequestInfo [" + productAddRequestInfo + "]");
		SellerAddCommand command = sellerMapper.toSellerAddCommand(productAddRequestInfo);
		command.setId(UUID.randomUUID().toString());
		commandDispatcher.send(command);
		
		URI location = new URI("/v1/seller/email/"+productAddRequestInfo.getSeller().getEmail());
				
				/*ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/email/{email}")
                .buildAndExpand(productAddRequestInfo.getSeller().getEmail())
                .toUri();*/
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setLocation(location);
	    
		return new ResponseEntity<>(ApiResponse.ofSuccess(201, "Seller and product detailes added successfully"), responseHeaders , HttpStatus.CREATED);
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
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<?> showProductByProductId(@NotBlank(message = "productId") @PathVariable String productId)
			throws ProductNotFoundException, InvalidOperationException {
		log.debug("showBidsByProduct  >>");
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, sellerService.getProductDetailsByProductId(productId)),
				HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<?> showAllSellerProductBySellerEmailId(@NotBlank(message = "email") @PathVariable String email)
			throws ProductNotFoundException, InvalidOperationException, SellerNotFoundException {
		log.debug("showBidsByProduct  >>");
		log.debug("email [" + email + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, sellerService.getAllSellerProductBySellerEmailId(email)),
				HttpStatus.OK);
	}
	
	@GetMapping("/{sellerId}")
	public ResponseEntity<?> showAllSellerProductBySellerId(@NotBlank(message = "sellerId") @PathVariable String sellerId)
			throws ProductNotFoundException, InvalidOperationException, SellerNotFoundException {
		log.debug("showBidsByProduct  >>");
		log.debug("sellerId [" + sellerId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, sellerService.getAllSellerProductBySellerId(sellerId)),
				HttpStatus.OK);
	}
	

}

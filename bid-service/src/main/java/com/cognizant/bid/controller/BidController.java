package com.cognizant.bid.controller;

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

import com.cognizant.bid.cqrs.commands.BidAddCommand;
import com.cognizant.bid.exception.InvalidOperationException;
import com.cognizant.bid.mapper.BidMapper;
import com.cognizant.bid.payload.ApiResponse;
import com.cognizant.bid.payload.BidRequestInfo;
import com.cognizant.bid.service.BidService;
import com.cognizant.cqrs.core.infrastructure.CommandDispatcher;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class BidController {
	
	private BidService bidService;
    private CommandDispatcher commandDispatcher;
    private BidMapper bidMapper;
	
	BidController(BidService bidService, CommandDispatcher commandDispatcher, BidMapper bidMapper){
		this.bidService = bidService;
		this.commandDispatcher = commandDispatcher;
		this.bidMapper = bidMapper;
	}
	
	@PostMapping("/buyer/place-bid")
	public ResponseEntity<?> placeBids(@RequestBody @Valid BidRequestInfo bidRequestInfo) throws Exception {
		log.debug("bidRequestInfo [" + bidRequestInfo + "]");
		BidAddCommand command = bidMapper.toBidAddCommand(bidRequestInfo);
		commandDispatcher.send(command);
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, "Bid placed successfully"), HttpStatus.OK);
	}
	
	@PutMapping("/buyer/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<?> updateBidAmount(@NotBlank(message = "Product Id cannot be blank") @PathVariable(value="productId") String productId,
			@NotBlank(message = "Email Id cannot be blank") @PathVariable(value = "buyerEmailId") String buyerEmailId,
			@NotBlank(message = "Bid amount cannot be blank") @PathVariable(value = "newBidAmount") double newBidAmount) throws Exception {
		
		log.debug("productId [" + productId + "] , buyerEmailId ["+buyerEmailId+"] , newBidAmount ["+newBidAmount+"]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, "Bid Amount updated successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/seller/show-bids/{productId}")
	public ResponseEntity<?> getAllBidsForProducts(@NotBlank(message = "Product Id cannot be blank") @PathVariable(value="productId") String productId) throws InvalidOperationException  {
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, bidService.getAllBidsDetailsByProduct(productId)), HttpStatus.OK);
	}

}

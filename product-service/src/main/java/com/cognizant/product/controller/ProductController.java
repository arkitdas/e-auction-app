package com.cognizant.product.controller;

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
import com.cognizant.product.cqrs.commands.ProductAddCommand;
import com.cognizant.product.cqrs.commands.ProductDeleteCommand;
import com.cognizant.product.exception.ProductNotFoundException;
import com.cognizant.product.mapper.ProductMapper;
import com.cognizant.product.payload.ApiResponse;
import com.cognizant.product.payload.ProductInfo;
import com.cognizant.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das
 */
@Slf4j
@RestController
@RequestMapping("/v1/product")
public class ProductController {
	
	private ProductService productService;
    private CommandDispatcher commandDispatcher;
    private ProductMapper productMapper;
	
	ProductController(ProductService productService, CommandDispatcher commandDispatcher, ProductMapper productMapper){
		this.productService = productService;
		this.commandDispatcher = commandDispatcher;
		this.productMapper = productMapper;
	}
	
	/*
	 * @PostMapping("/add") public ResponseEntity<?> addProduct(@RequestBody @Valid
	 * ProductInfo productInfo) { log.debug("addProduct  >>");
	 * log.debug("productInfo [" + productInfo + "]"); return new
	 * ResponseEntity<>(ApiResponse.ofSuccess(200,
	 * productService.addProduct(productInfo)), HttpStatus.OK); }
	 */
	
	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody @Valid ProductInfo productInfo) {
		log.debug("addProduct  >>");
		log.debug("productInfo [" + productInfo + "]");
		ProductAddCommand command = productMapper.toProductAddCommand(productInfo);
		command.setId(UUID.randomUUID().toString());
		commandDispatcher.send(command);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productId}")
                .buildAndExpand(command.getId())
                .toUri();
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setLocation(location);
		return new ResponseEntity<>(ApiResponse.ofSuccess(201, "Product added successfully"), responseHeaders, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteProduct(@NotBlank(message = "productId") @PathVariable String productId) throws ProductNotFoundException {
		log.debug("deleteProduct  >>");
		log.debug("productId [" + productId + "]");
		ProductDeleteCommand command = ProductDeleteCommand.builder().productId(productId).build();
		commandDispatcher.send(command);
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, "Product Deleted Successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProduct(@NotBlank(message = "productId") @PathVariable String productId) throws ProductNotFoundException {
		log.debug("getProduct  >>");
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, productService.getProductByProductId(productId)), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllProduct() throws ProductNotFoundException {
		log.debug("getAllProduct  >>");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, productService.getAllProduct()), HttpStatus.OK);
	}

}

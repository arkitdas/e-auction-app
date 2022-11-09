package com.cognizant.product.controller;

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

import com.cognizant.product.exception.ProductNotFoundException;
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
	
	ProductController(ProductService productService){
		this.productService = productService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody @Valid ProductInfo productInfo) {
		log.debug("addProduct  >>");
		log.debug("productInfo [" + productInfo + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, productService.addProduct(productInfo)), HttpStatus.OK);
	}
	
	@PostMapping("/add/cqrs")
	public ResponseEntity<?> addProductCQRS(@RequestBody @Valid ProductInfo productInfo) {
		log.debug("addProduct  >>");
		log.debug("productInfo [" + productInfo + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, productService.addProductCQRS(productInfo)), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteProduct(@NotBlank(message = "productId") @PathVariable String productId) throws ProductNotFoundException {
		log.debug("deleteProduct  >>");
		log.debug("productId [" + productId + "]");
		return new ResponseEntity<>(ApiResponse.ofSuccess(200, productService.deleteProduct(productId)), HttpStatus.OK);
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

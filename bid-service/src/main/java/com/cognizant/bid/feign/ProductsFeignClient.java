package com.cognizant.bid.feign;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognizant.bid.payload.ApiResponse;
import com.cognizant.bid.payload.ProductResponseInfo;

@FeignClient(name = "${bid.remote.services.product.name}", path = "/product-service/v1")
public interface ProductsFeignClient {

    @DeleteMapping("/seller/delete/{productId}")
	public ResponseEntity<ApiResponse<Boolean>> deleteProduct(@NotBlank(message = "Product ID cannot be blank") @PathVariable(value = "productId") String productId);
    
    @GetMapping("/product/{productId}")
	public ResponseEntity<ApiResponse<ProductResponseInfo>> getProduct(@NotBlank(message = "Product ID cannot be blank") @PathVariable(value = "productId") String productId);
	
    @GetMapping("/product")
    public ResponseEntity<ApiResponse<List<ProductResponseInfo>>> getAllProduct();
}

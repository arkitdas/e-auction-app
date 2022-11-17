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

@FeignClient(name = "${buyer.remote.services.product.name}", path = "/product-service/v1/product")
public interface ProductsFeignClient {

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteProduct(@NotBlank(message = "productId") @PathVariable String productId);
    
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseInfo>> getProduct(@NotBlank(message = "productId") @PathVariable String productId);
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseInfo>>> getAllProduct();
}

package com.cognizant.buyer.feign;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cognizant.buyer.payload.ApiResponse;
import com.cognizant.buyer.payload.ProductInfo;
import com.cognizant.buyer.payload.ProductResponseInfo;

@FeignClient(url = "${buyer.remote.services.product.url:#{null}}", name = "${buyer.remote.services.product.name}", path = "/v1/product")
public interface ProductsFeignClient {

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ProductResponseInfo>> addProduct(ProductInfo productInfo);
    
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteProduct(@NotBlank(message = "productId") @PathVariable String productId);
    
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseInfo>> getProduct(@NotBlank(message = "productId") @PathVariable String productId);
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseInfo>>> getAllProduct();
}

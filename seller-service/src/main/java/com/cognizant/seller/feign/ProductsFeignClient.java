package com.cognizant.seller.feign;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cognizant.seller.payload.ApiResponse;
import com.cognizant.seller.payload.ProductInfo;
import com.cognizant.seller.payload.ProductResponseInfo;

@FeignClient(name = "${seller.remote.services.product.name}", path = "/product-service/v1/product")
public interface ProductsFeignClient {

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ProductResponseInfo>> addProduct(ProductInfo productInfo);
    
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteProduct(@NotBlank(message = "productId") @PathVariable String productId);
    
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseInfo>> getProduct(@NotBlank(message = "productId") @PathVariable String productId);
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseInfo>>> getAllProduct();
    
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<List<ProductResponseInfo>>> getAllProductBySeller(@NotBlank(message = "sellerId") @PathVariable String sellerId);
}

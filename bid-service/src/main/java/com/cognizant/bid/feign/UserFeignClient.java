package com.cognizant.bid.feign;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognizant.bid.payload.ApiResponse;
import com.cognizant.bid.payload.UserRequestInfo;
import com.cognizant.bid.payload.UserResponseInfo;

@FeignClient(name = "${bid.remote.services.user.name}", path = "/user-service/v1/user")
public interface UserFeignClient {

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseInfo>> addUser(@RequestBody UserRequestInfo userRequestInfo);
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteUser(@NotBlank(message = "User Id cannot be blank") @PathVariable(value = "userId") String userId);
    
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseInfo>> getUserByUserId(@NotBlank(message = "User Id cannot be blank") @PathVariable(value = "userId") String userId);
    
    @GetMapping("/email/{emailId}")
    public ResponseEntity<ApiResponse<UserResponseInfo>> getUserByEmailId(@NotBlank(message = "Email Id cannot be blank") @PathVariable(value = "emailId") String emailId);
}

package com.cognizant.product.client;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cognizant.product.feign.UserFeignClient;
import com.cognizant.product.payload.ApiResponse;
import com.cognizant.product.payload.ProductResponseInfo;
import com.cognizant.product.payload.UserRequestInfo;
import com.cognizant.product.payload.UserResponseInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arkit Das 
 * Client utility class for communicating with product-service
 */
@Component
@Slf4j
public class UserClient {

	private UserFeignClient userFeignClient;
	
	UserClient(UserFeignClient userFeignClient){
		this.userFeignClient = userFeignClient;
	}
	
	public UserResponseInfo addUser(UserRequestInfo request) {
		ApiResponse<UserResponseInfo> response = userFeignClient.addUser(request).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (UserResponseInfo) response.getPayload();
        }else{
            log.error("Failed to credit transaction");
            return null;
        }
	}
	
	public UserResponseInfo getUserByUserId(String userId) {
		ApiResponse<UserResponseInfo> response = userFeignClient.getUserByUserId(userId).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (UserResponseInfo) response.getPayload();
        }else{
            log.error("Failed to credit transaction");
            return null;
        }
	}
	
	
	public UserResponseInfo getUserByEmailId(String emailId) {
		ApiResponse<UserResponseInfo> response = userFeignClient.getUserByEmailId(emailId).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (UserResponseInfo) response.getPayload();
        }else{
            log.error("Failed to credit transaction");
            return null;
        }
	}
	
	public boolean deleteUser(String userId) {
		ApiResponse<Boolean> response = userFeignClient.deleteUser(userId).getBody();
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (Boolean) response.getPayload();
        }else{
            log.error("Failed to credit transaction");
            return false;
        }
	}
	
}

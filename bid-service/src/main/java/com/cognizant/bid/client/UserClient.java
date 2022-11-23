package com.cognizant.bid.client;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cognizant.bid.feign.UserFeignClient;
import com.cognizant.bid.payload.ApiResponse;
import com.cognizant.bid.payload.UserRequestInfo;
import com.cognizant.bid.payload.UserResponseInfo;

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
		ResponseEntity<ApiResponse<UserResponseInfo>> responseEntity = userFeignClient.addUser(request);
		ApiResponse<UserResponseInfo> response = null;
		if(responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
			response = userFeignClient.getUserByEmailId(request.getEmail()).getBody();
		}
        
        if (!Objects.isNull(response) && response.isSuccess()
                && !Objects.isNull(response.getPayload())) {
            return (UserResponseInfo) response.getPayload();
        }else{
            log.error("Failed to add user details");
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

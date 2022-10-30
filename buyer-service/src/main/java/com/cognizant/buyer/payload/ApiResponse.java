package com.cognizant.buyer.payload;



import org.springframework.http.HttpStatus;

import com.cognizant.buyer.exception.ProductNotFoundException;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Arkit Das
 */
@Getter
@ToString
@Builder
@Setter
public class ApiResponse<T> {

	private boolean success;
	private int code;
	private String message;
	private T payload;
	private ApiError error;
	
	public ApiResponse() {
		
	}

	public ApiResponse(boolean success, int code, String message, T payload, ApiError error) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.payload = payload;
		this.error = error;
	}

	public static <T extends Object> ApiResponse<T> ofSuccess(String message) {
		return ofSuccess(HttpStatus.OK.value(), null, message);
	}
	
	public static <T extends Object> ApiResponse<T> ofSuccess(int code, String message) {
		return ofSuccess(code, null, message);
	}
	
	public static <T extends Object> ApiResponse<T> ofSuccess(int code, T payload) {
		return ofSuccess(code, payload, null);
	}

	public static <T extends Object> ApiResponse<T> ofSuccess(T payload, String message) {
		return ofSuccess(HttpStatus.OK.value(), payload, message);
	}

	public static <T extends Object> ApiResponse<T> ofSuccess(int code, T payload, String message) {
		return new ApiResponse<>(true, code, message, payload, null);
	}

	public static <T extends Object> ApiResponse<T> ofFailure(String message) {
		return ofFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, message);
	}

	public static <T extends Object> ApiResponse<T> ofFailure(int code, String message) {
		return ofFailure(code, null, message);
	}

	public static <T extends Object> ApiResponse<T> ofFailure(int code, ApiError error) {
		return ofFailure(code, error, null);
	}

	public static <T extends Object> ApiResponse<T> ofFailure(ApiError error, String message) {
		return ofFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), error, message);
	}

	public static <T extends Object> ApiResponse<T> ofFailure(int code, ApiError error, String message) {
		return new ApiResponse<>(false, code, message, null, error);
	}

	public static <T extends Object> ApiResponse<T> fromException(ProductNotFoundException e) {
		return new ApiResponse<>(false, e.getCode(), e.getMessage(), null, null);
	}
}

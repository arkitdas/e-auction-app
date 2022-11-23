package com.cognizant.user.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1433491843826519483L;

	private static final String DEFAULT_MSG = "Internal Server Error";

	private int code;

	private String message;

	private Throwable cause;

	public UserNotFoundException() {
		this(HttpStatus.BAD_REQUEST.value(), DEFAULT_MSG, null);
	}

	public UserNotFoundException(String message) {
		this(HttpStatus.BAD_REQUEST.value(), message, null);
	}

	public UserNotFoundException(int code, String message) {
		this(code, message, null);
	}

	public UserNotFoundException(String message, Throwable cause) {
		this(HttpStatus.BAD_REQUEST.value(), message, cause);
	}

	public UserNotFoundException(int code, String message, Throwable cause) {
		this.code = code;
		this.message = message;
		this.cause = cause;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

}


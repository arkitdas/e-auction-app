package com.cognizant.auth.payload;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arkit Das
 */
public class FieldError {

	private String field;
	
	private List<String> messages = new ArrayList<>();

	public FieldError() {}

	private FieldError(String field) {
		super();
		this.field = field;
	}
	
	private FieldError(String field, List<String> messages) {
		super();
		this.field = field;
		this.messages = messages;
	}

	public static FieldError of(String field, List<String> messages) {
		return new FieldError(field, messages);
	}
	public static FieldError of(String field) {
		return new FieldError(field);
	}

	public String getField() {
		return field;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}
}
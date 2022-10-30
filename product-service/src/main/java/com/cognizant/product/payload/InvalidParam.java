package com.cognizant.product.payload;

public class InvalidParam {

	private String param;

	private String reason;

	public InvalidParam() {

	}

	public InvalidParam(String param, String reason) {
		this.param = param;
		this.reason = reason;
	}
	
	public String getParam() {
		return param;
	}

	public String getReason() {
		return reason;
	}

	public static class Builder {

		private String paramName;

		private String paramError;

		public Builder(){}

		public Builder param(final String param) {
			this.paramName = param;
			return this;
		}

		public Builder reason(final String error) {
			this.paramError = error;
			return this;
		}

		public InvalidParam build() {
			return new InvalidParam(paramName, paramError);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public void setParam(String param) {
		this.param = param;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "InvalidParam [param=" + param + ", reason=" + reason + "]";
	}

}

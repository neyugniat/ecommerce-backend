package com.neyugniat.response;

public class ApiResponse {
	private boolean status;
	private String jwt;
	private String message;
	
	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public ApiResponse(boolean status, String jwt, String message) {
		super();
		this.status = status;
		this.jwt = jwt;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

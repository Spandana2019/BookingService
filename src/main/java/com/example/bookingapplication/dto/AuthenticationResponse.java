package com.example.bookingapplication.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String code;
	private Boolean status;
	public AuthenticationResponse(String code, Boolean status) {
		this.code = code;
		this.status = status;
	}
	
	

}

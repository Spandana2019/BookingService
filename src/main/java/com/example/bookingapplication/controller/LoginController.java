package com.example.bookingapplication.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingapplication.dto.AuthenticationResponse;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LoginController {

	@GetMapping("/login")
	public ResponseEntity<AuthenticationResponse> login() {
		
		AuthenticationResponse map = new AuthenticationResponse(new Random().nextInt()+"", true);
		return new ResponseEntity<>(map, HttpStatus.OK);
	
	}
	
	@GetMapping("/logout")
	public ResponseEntity<Boolean> logout(){
		//invalidate the code
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}

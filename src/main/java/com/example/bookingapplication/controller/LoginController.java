package com.example.bookingapplication.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
public class LoginController {

	@GetMapping
	public Principal getUser(Principal user) {
		return user;
	}
}

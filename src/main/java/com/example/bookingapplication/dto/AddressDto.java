package com.example.bookingapplication.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddressDto {

	@Size(max = 100)
	private String street;
	@Size(max = 100)
	private String city;
	@Size(max = 100)
	private String state;
	@Size(max = 5)
	private String zip;
}

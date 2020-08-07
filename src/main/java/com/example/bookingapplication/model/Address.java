package com.example.bookingapplication.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

import lombok.Data;

@Data
@Embeddable
@Table(name = "Address")
public class Address {

	@Column(name = "Street")
	private String street;
	@Column(name = "City")
	private String city;
	@Column(name = "State")
	private String state;
	@Column(name = "Zip")
	private String zip;
}

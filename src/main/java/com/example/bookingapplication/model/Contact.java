package com.example.bookingapplication.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.bookingapplication.annotations.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
@Entity
@Table(name = "Contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Contactid")
	private long contactId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Email")
	private String email;
	@Column(name = "Phone")
	private String phone;
	@Embedded
	private Address address;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "Bookingid")
	private Booking booking;
	@JsonFormat(shape=Shape.STRING, pattern = "MM-dd-yyyy")
	@Column(name="Dateofbirth")
	private LocalDate dateOfBirth;
	@NotNull
	@Gender
	@Column(name="Gender")
	private String gender;
}

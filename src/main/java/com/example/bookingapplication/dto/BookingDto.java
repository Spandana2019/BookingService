package com.example.bookingapplication.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.example.bookingapplication.annotations.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class BookingDto {

	private long bookingId;
	@NotNull
	@Size(max = 65)
	private String name;
	@Email
	@NotNull
	@Size(max = 100)
	private String email;
	@NotNull
	@Size(max=12, min=12, message="Phone should be of length 12")
	@Pattern(regexp = "[0-9\\-]*", message="Phone is not in correct format please follow XXX-XXX-XXXX")
	private String phone;
	@Valid
	@NotNull(message = "Slot is required")
	private TimeSlotDto slot;
	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy")
	@NotNull
	private LocalDate bookingDate;
	@Valid
	private AddressDto address;
	@JsonFormat(shape=Shape.STRING, pattern = "MM-dd-yyyy")
	@Past
	@NotNull
	private LocalDate dateOfBirth;
	@NotNull
	@Gender
	private String gender;
	private boolean subscription = false;

}

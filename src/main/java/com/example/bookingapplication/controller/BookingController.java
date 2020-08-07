package com.example.bookingapplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingapplication.dto.BookingDto;
import com.example.bookingapplication.dto.BookingResponse;
import com.example.bookingapplication.model.Booking;
import com.example.bookingapplication.service.BookingService;
import com.example.bookingapplication.utility.Mapper;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

	private BookingService bookingService;
	private Mapper mapper;

	public BookingController(BookingService bookingService, Mapper mapper) {
		this.bookingService = bookingService;
		this.mapper = mapper;
	}

	@GetMapping
	public ResponseEntity<BookingResponse<BookingDto>> getAllBookings() {
		List<Booking> bookings = bookingService.getAllBookings();
		List<BookingDto> bookingsDto = bookings.stream().map(x -> mapper.mapModelToDto(x)).collect(Collectors.toList());
		BookingResponse<BookingDto> response = new BookingResponse<>(bookingsDto);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingDto> getBooking(@PathVariable long id) {
		return new ResponseEntity<>(mapper.mapModelToDto(bookingService.getBooking(id)), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BookingDto> addBooking(@RequestBody @Valid BookingDto bookingDto) {
		Booking booking = bookingService.addBooking(mapper.mapDtoToModel(bookingDto));
		return new ResponseEntity<>(mapper.mapModelToDto(booking), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BookingResponse<BookingDto>> deleteBooking(@PathVariable long id) {
		List<BookingDto> list = bookingService.deleteBooking(id).stream().map(x -> mapper.mapModelToDto(x))
				.collect(Collectors.toList());
		return new ResponseEntity<>(new BookingResponse<>(list), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<BookingDto> updateBooking(@RequestBody BookingDto bookingDto) {
		return new ResponseEntity<>(
				mapper.mapModelToDto(bookingService.updateBooking(mapper.mapDtoToModel(bookingDto))), HttpStatus.OK);
	}

}

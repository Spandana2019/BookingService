package com.example.bookingapplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingapplication.dto.BookingResponse;
import com.example.bookingapplication.dto.TimeSlotDto;
import com.example.bookingapplication.model.TimeSlot;
import com.example.bookingapplication.service.TimeSlotService;
import com.example.bookingapplication.utility.Mapper;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin
public class SlotsController {

	private TimeSlotService timeSlotService;
	private Mapper mapper;

	SlotsController(TimeSlotService timeSlotService, Mapper mapper) {
		super();
		this.timeSlotService = timeSlotService;
		this.mapper = mapper;
	}

	@GetMapping("/2")
	public ResponseEntity<BookingResponse<TimeSlotDto>> getAllAvailableSlots2() {
		List<TimeSlotDto> list = timeSlotService.getAllAvailableTimeSlots().stream().map(x -> mapper.mapModelToDto(x))
				.collect(Collectors.toList());
		return new ResponseEntity<>(new BookingResponse<TimeSlotDto>(list), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<TimeSlotDto>> getAllAvailableSlots() {
		List<TimeSlotDto> list = timeSlotService.getAllAvailableTimeSlots().stream().map(x -> mapper.mapModelToDto(x))
				.collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TimeSlotDto> addSlot(@RequestBody @Valid TimeSlotDto timeSlotDto) {
		return new ResponseEntity<>(
				mapper.mapModelToDto(timeSlotService.addTimeSlot(mapper.mapDtoToModel(timeSlotDto))), HttpStatus.OK);

	}

}

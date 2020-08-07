package com.example.bookingapplication.dto;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class TimeSlotDto {

	private long slotId;
	@JsonFormat(shape = Shape.STRING, pattern = "MM-dd-yyyy")
	@NotNull
	private LocalDate date;
	@JsonFormat(shape = Shape.STRING, pattern = "hh:mm a")
	@NotNull
	private LocalTime startTime;
	@JsonFormat(shape = Shape.STRING, pattern = "hh:mm a")
	@NotNull
	private LocalTime endTime;
	private boolean available = true;

}

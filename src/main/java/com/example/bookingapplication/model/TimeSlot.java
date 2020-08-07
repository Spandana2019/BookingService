package com.example.bookingapplication.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Timeslot")
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Slotid")
	private long slotId;
	@Column(name = "Date")
	private LocalDate date;
	@Column(name = "Starttime")
	private LocalTime startTime;
	@Column(name = "Endtime")
	private LocalTime endTime;
	@Column(name = "Available")
	private boolean available;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Bookingid")
	private Booking booking;

}

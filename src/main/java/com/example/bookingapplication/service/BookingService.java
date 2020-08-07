package com.example.bookingapplication.service;

import java.util.List;

import com.example.bookingapplication.model.Booking;

public interface BookingService {

	List<Booking> getAllBookings();

	Booking getBooking(long id);

	Booking addBooking(Booking booking);

	Booking updateBooking(Booking booking);

	List<Booking> deleteBooking(long bookingId);

}

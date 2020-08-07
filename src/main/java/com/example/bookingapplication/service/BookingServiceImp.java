package com.example.bookingapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingapplication.exception.BookingException;
import com.example.bookingapplication.model.Booking;
import com.example.bookingapplication.model.TimeSlot;
import com.example.bookingapplication.repository.BookingRepository;
import com.example.bookingapplication.utility.Mapper;

@Service
public class BookingServiceImp implements BookingService {

	private BookingRepository bookingRepository;
	private TimeSlotService timeSlotService;
	private Mapper mapper;

	public BookingServiceImp(BookingRepository bookingRepository, TimeSlotService timeSlotService, Mapper mapper) {
		this.bookingRepository = bookingRepository;
		this.timeSlotService = timeSlotService;
		this.mapper = mapper;
	}

	@Override
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking addBooking(Booking booking) {
		TimeSlot timeSlot = timeSlotService.getTimeSlot(booking.getSlot().getSlotId());
		timeSlot.setAvailable(false);
		timeSlotService.updateTimeSlot(timeSlot);
		booking.setSlot(timeSlot);
		timeSlot.setBooking(booking);
		return bookingRepository.save(booking);
	}

	@Override
	public Booking updateBooking(Booking booking) {
		Booking existingBooking = getBooking(booking.getBookingId());
		mapper.mapModelToExistingModel(existingBooking, booking);
		if (existingBooking.getSlot().getSlotId() != booking.getSlot().getSlotId()) {
			TimeSlot timeSlot = timeSlotService.getTimeSlot(existingBooking.getSlot().getSlotId());
			timeSlot.setAvailable(true);
			timeSlot.setBooking(null);
			timeSlotService.updateTimeSlot(timeSlot);
			timeSlot = timeSlotService.getTimeSlot(booking.getSlot().getSlotId());
			timeSlot.setAvailable(false);
			timeSlot.setBooking(existingBooking);
			timeSlotService.updateTimeSlot(timeSlot);
			existingBooking.setSlot(timeSlot);
		}
		return bookingRepository.save(existingBooking);
	}

	@Override
	public List<Booking> deleteBooking(long bookingId) {
		TimeSlot timeSlot = timeSlotService.getTimeSlot(getBooking(bookingId).getSlot().getSlotId());
		timeSlot.setAvailable(true);
		timeSlot.setBooking(null);
		timeSlotService.updateTimeSlot(timeSlot);
		bookingRepository.deleteById(bookingId);
		return bookingRepository.findAll();
	}

	@Override
	public Booking getBooking(long id) {
		return bookingRepository.findById(id).orElseThrow(() -> new BookingException("Could not find Booking"));
	}

}

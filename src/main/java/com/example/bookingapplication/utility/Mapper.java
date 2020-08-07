package com.example.bookingapplication.utility;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import com.example.bookingapplication.dto.AddressDto;
import com.example.bookingapplication.dto.BookingDto;
import com.example.bookingapplication.dto.TimeSlotDto;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.Booking;
import com.example.bookingapplication.model.Contact;
import com.example.bookingapplication.model.TimeSlot;

@Component
public class Mapper {

	private Helper helper;

	private static final String DATE_PATTERN = "MM-dd-yyyy";
	
	public Mapper(Helper helper) {
		this.helper = helper;
	}

	public Booking mapDtoToModel(BookingDto bookingDto) {
		if (bookingDto == null)
			return null;
		Booking booking = new Booking();
		if (bookingDto.getBookingId() > 0) {
			booking.setBookingId(bookingDto.getBookingId());
		}
		booking.setSubscription(bookingDto.isSubscription());
		booking.setBookingDate(bookingDto.getBookingDate());
		Contact contact = new Contact();
		contact.setName(bookingDto.getName());
		contact.setEmail(bookingDto.getEmail());
		contact.setPhone(bookingDto.getPhone());
		contact.setGender(bookingDto.getGender());
		contact.setDateOfBirth(bookingDto.getDateOfBirth());
		if (bookingDto.getAddress() != null)
			contact.setAddress(mapDtoToModel(bookingDto.getAddress()));
		contact.setBooking(booking);
		booking.setContact(contact);
		if (bookingDto.getSlot() != null) {
			TimeSlot timeSlot = mapDtoToModel(bookingDto.getSlot());
			timeSlot.setBooking(booking);
			booking.setSlot(timeSlot);
		}
		return booking;
	}

	public Address mapDtoToModel(AddressDto addressDto) {
		Address address = new Address();
		address.setStreet(addressDto.getStreet());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setZip(addressDto.getZip());
		return address;
	}

	public BookingDto mapModelToDto(Booking booking) {
		if (booking == null)
			return null;
		Contact contact = booking.getContact();
		BookingDto bookingDto = new BookingDto();
		bookingDto.setName(contact.getName());
		bookingDto.setEmail(contact.getEmail());
		bookingDto.setPhone(contact.getPhone());
		bookingDto.setDateOfBirth(contact.getDateOfBirth());
		bookingDto.setGender(contact.getGender());
		if (contact.getAddress() != null)
			bookingDto.setAddress(mapModelToDto(contact.getAddress()));
		bookingDto.setBookingId(booking.getBookingId());
		bookingDto.setBookingDate(booking.getBookingDate());
		bookingDto.setSubscription(booking.isSubscription());
		if (booking.getSlot() != null) {
			TimeSlotDto timeSlotDto = mapModelToDto(booking.getSlot());
			bookingDto.setSlot(timeSlotDto);
		}
		return bookingDto;
	}

	public AddressDto mapModelToDto(Address address) {
		AddressDto addressDto = new AddressDto();
		addressDto.setStreet(address.getStreet());
		addressDto.setCity(address.getCity());
		addressDto.setState(address.getState());
		addressDto.setZip(address.getZip());
		return addressDto;
	}

	public TimeSlot mapDtoToModel(TimeSlotDto timeSlotDto) {
		if (timeSlotDto == null)
			return null;
		TimeSlot timeSlot = new TimeSlot();
		timeSlot.setAvailable(timeSlotDto.isAvailable());
		timeSlot.setDate(timeSlotDto.getDate());
		timeSlot.setStartTime(timeSlotDto.getStartTime());
		timeSlot.setEndTime(timeSlotDto.getEndTime());
		if (timeSlotDto.getSlotId() > 0) {
			timeSlot.setSlotId(timeSlotDto.getSlotId());
		}
		return timeSlot;
	}

	public TimeSlotDto mapModelToDto(TimeSlot timeSlot) {
		if (timeSlot == null)
			return null;
		TimeSlotDto timeSlotDto = new TimeSlotDto();
		timeSlotDto.setAvailable(timeSlot.isAvailable());
		timeSlotDto.setDate(timeSlot.getDate());
		timeSlotDto.setStartTime(timeSlot.getStartTime());
		timeSlotDto.setEndTime(timeSlot.getEndTime());
		timeSlotDto.setSlotId(timeSlot.getSlotId());
		return timeSlotDto;
	}

	public void mapModelToExistingModel(Booking existingBooking, Booking changedBooking) {
		if (existingBooking == null || changedBooking == null)
			return;
		if (changedBooking.getBookingDate() != null)
			existingBooking.setBookingDate(changedBooking.getBookingDate());
		existingBooking.setSubscription(changedBooking.isSubscription());
		if (changedBooking.getContact() != null) {
			if (changedBooking.getContact().getName() != null)
				existingBooking.getContact().setName(changedBooking.getContact().getName());
			if (changedBooking.getContact().getEmail() != null)
				existingBooking.getContact().setEmail(changedBooking.getContact().getEmail());
			if (changedBooking.getContact().getPhone() != null)
				existingBooking.getContact().setPhone(changedBooking.getContact().getPhone());
			if (changedBooking.getContact().getDateOfBirth() != null)
				existingBooking.getContact().setDateOfBirth(changedBooking.getContact().getDateOfBirth());
			if (changedBooking.getContact().getGender() != null)
				existingBooking.getContact().setGender(changedBooking.getContact().getGender());
			if (changedBooking.getContact().getAddress() != null) {
				if (changedBooking.getContact().getAddress().getStreet() != null) {
					existingBooking.getContact().getAddress()
							.setStreet(changedBooking.getContact().getAddress().getStreet());
				}
				if (changedBooking.getContact().getAddress().getCity() != null) {
					existingBooking.getContact().getAddress()
							.setCity(changedBooking.getContact().getAddress().getCity());
				}
				if (changedBooking.getContact().getAddress().getState() != null) {
					existingBooking.getContact().getAddress()
							.setState(changedBooking.getContact().getAddress().getState());
				}
				if (changedBooking.getContact().getAddress().getZip() != null) {
					existingBooking.getContact().getAddress().setZip(changedBooking.getContact().getAddress().getZip());
				}
			}
		}

	}
}

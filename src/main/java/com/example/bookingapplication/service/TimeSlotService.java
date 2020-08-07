package com.example.bookingapplication.service;

import java.util.List;

import com.example.bookingapplication.model.TimeSlot;

public interface TimeSlotService {

	List<TimeSlot> getAllAvailableTimeSlots();

	TimeSlot getTimeSlot(long slotId);

	TimeSlot addTimeSlot(TimeSlot timeSlot);

	TimeSlot updateTimeSlot(TimeSlot timeSlot);

	void deleteTimeSlot(TimeSlot timeSlot);

	// void updateTimeSlotUnavailable(Long slotId);

}

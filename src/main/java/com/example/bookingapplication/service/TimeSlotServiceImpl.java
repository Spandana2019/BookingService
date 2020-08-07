package com.example.bookingapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bookingapplication.exception.BookingException;
import com.example.bookingapplication.model.TimeSlot;
import com.example.bookingapplication.repository.TimeSlotRepository;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

	private TimeSlotRepository timeSlotRepository;

	public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository) {
		this.timeSlotRepository = timeSlotRepository;
	}

	@Override
	public List<TimeSlot> getAllAvailableTimeSlots() {
		List<TimeSlot> slots = timeSlotRepository.findAllAvailableSlots().stream().sorted((x,y)->{
			if(x.getDate().isEqual(y.getDate())){
			if(x.getStartTime().equals(y.getStartTime())) return 0; else { 
			if(x.getStartTime().isBefore(y.getStartTime())) return -1; else return 1;
			}
		    }else {
			if(x.getDate().isBefore(y.getDate())) return -1;return 1;
		    }
		}
		).collect(Collectors.toList());
		
		slots = timeSlotRepository.findAllAvailableSlots();
		
		//System.out.println(slots.get(0).getStartTime().isAfter(slots.get(1).getStartTime()));
		
		slots.forEach(x->System.out.println(x));
		
		return slots;
	}

	@Override
	public TimeSlot addTimeSlot(TimeSlot timeSlot) {
		return timeSlotRepository.save(timeSlot);
	}

	@Override
	public TimeSlot updateTimeSlot(TimeSlot timeSlot) {
		return timeSlotRepository.save(timeSlot);
	}

	@Override
	public void deleteTimeSlot(TimeSlot timeSlot) {
		timeSlotRepository.delete(timeSlot);
	}

	@Override
	public TimeSlot getTimeSlot(long slotId) {
		return timeSlotRepository.findById(slotId).orElseThrow(()->new BookingException("Invalid Time Slot"));
	}

	/*@Override
	@Transactional
	public void updateTimeSlotUnavailable(Long slotId) {
		timeSlotRepository.updateTimeSlotToUnavailable(slotId);
	}*/

}

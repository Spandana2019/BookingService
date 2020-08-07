package com.example.bookingapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingapplication.model.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

	@Query(value = "Select * from TimeSlot where available='true' order by date,startTime", nativeQuery = true)
	List<TimeSlot> findAllAvailableSlots();

	List<TimeSlot> findByAvailable(Boolean flag);

	@Modifying
	@Query(value = "Update TimeSlot set available='false' where slotId=:slotId", nativeQuery = true)
	Integer updateTimeSlotToUnavailable(long slotId);

}

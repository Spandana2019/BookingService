package com.example.bookingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingapplication.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}

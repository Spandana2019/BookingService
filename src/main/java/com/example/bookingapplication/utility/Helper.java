package com.example.bookingapplication.utility;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Helper {

	public DateTimeFormatter getFormatter(String pattern) {
		return DateTimeFormatter.ofPattern(pattern);
	}
}

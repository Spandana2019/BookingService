package com.example.bookingapplication.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleExcpetion(MethodArgumentNotValidException e) {
		Map<String, String> mapErrors = e.getBindingResult().getAllErrors().stream()
				.collect(Collectors.toMap(x -> ((FieldError) x).getField(), x -> x.getDefaultMessage()));
		return new ResponseEntity<>(mapErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class, BookingException.class })
	public ResponseEntity<Map<String, String>> handleGeneralExcpetion(Exception e) {
		Map<String, String> mapErrors = new HashMap<>();
		mapErrors.put("message", "There was an internal server error");
		logger.error("Error", e);
		return new ResponseEntity<>(mapErrors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

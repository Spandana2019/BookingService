package com.example.bookingapplication.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BookingResponse<T> {

	private List<T> list = new ArrayList<T>();

	public BookingResponse(List<T> list) {
		this.list.addAll(list);
	}

	public BookingResponse() {
	}

}

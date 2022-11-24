package com.mandeep.carrental.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingResponse;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.services.BookingService;

@Service
public class BookingServiceImpl implements BookingService{

	@Override
	public AvailbleCarResponse getAvailableCars(LocalDateTime from, LocalDateTime till, String parkingSlotId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingResponse bookCar(BookingRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}

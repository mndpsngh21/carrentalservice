package com.mandeep.carrental.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingResponse;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.services.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@PostMapping("/available")
	public AvailbleCarResponse getAvailableCars(@RequestParam("from") LocalDateTime from,@RequestParam("till") LocalDateTime till,@RequestParam("parkingslotId") String parkingSlotId) {
		return bookingService.getAvailableCars(from,till,parkingSlotId);
	}

	
	@PostMapping("/book")
	public BookingResponse bookCar(BookingRequest request) {
		return bookingService.bookCar(request);
	}
	
	
}

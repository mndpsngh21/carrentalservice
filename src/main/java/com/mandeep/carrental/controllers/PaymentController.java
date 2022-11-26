package com.mandeep.carrental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.services.BookingService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	BookingService bookingService;

	
	@PostMapping("/confirm")
	public BookingResponse confirmBooking(BookingConfirmationRequest request) {
		return bookingService.confirmBooking(request);
	}
	
	@PostMapping("/canceled")
	public BookingResponse cancelBooking(BookingConfirmationRequest request) {
		return bookingService.confirmBooking(request);
	}
	
	
}

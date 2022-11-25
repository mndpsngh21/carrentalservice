package com.mandeep.carrental.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mandeep.carrental.requests.BookingCancelRequest;
import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.VehicleSearchService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	VehicleSearchService searchService;
	
	@PostMapping("/availableByParkingSlot")
	public AvailbleCarResponse getAvailableCars(@RequestParam("from") LocalDateTime from,@RequestParam("till") LocalDateTime till,@RequestParam("parkingslotId") String parkingSlotId) {
		return bookingService.getAvailableCars(from,till,parkingSlotId);
	}

	@PostMapping("/availableByCity")
	public AvailbleCarResponse getAvailableCarsByCty(
	@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime from,
	
	@RequestParam("till") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")	 
	LocalDateTime till,
	@RequestParam("city") String city) {
		return bookingService.getAvailableCarsByCty(from,till,city);
	}

	
	@PostMapping("/book")
	public BookingResponse bookCar(BookingRequest request) {
		return bookingService.bookCar(request);
	}
	
	@PostMapping("/cancel")
	public BookingResponse bookCar(BookingCancelRequest request) {
		return bookingService.cancelRequest(request);
	}
	
	
	
	@PostMapping("/confirm")
	public BookingResponse confirmBooking(BookingConfirmationRequest request) {
		return bookingService.confirmBooking(request);
	}
	
	
	
	
}

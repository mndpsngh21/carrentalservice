package com.mandeep.carrental.controllers;

import java.time.LocalDateTime;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.requests.BookingCancelRequest;
import com.mandeep.carrental.requests.BookingCompleteRequest;
import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingCompletionResponse;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.responses.UserBookingResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.VehicleSearchService;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

	@Autowired
	BookingService bookingService;

	@Autowired
	VehicleSearchService searchService;

	@Value("${url.estimate}")
	String estimateURL;

	@Value("${url.final}")
	String finalURL;

	@PostMapping("/availableByParkingSlot")
	public AvailbleCarResponse getAvailableCars(@RequestParam("from") LocalDateTime from,
			@RequestParam("till") LocalDateTime till, @RequestParam("parkingslotId") String parkingSlotId) {
		return bookingService.getAvailableCars(from, till, parkingSlotId);
	}

	/**
	 *  //TODO we need to handle case of if pickup and drop locations are different
	 * @param from
	 * @param till
	 * @param city
	 * @return
	 */
	@PostMapping("/availableByCity")
	public AvailbleCarResponse getAvailableCarsByCty(
			@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
			@RequestParam("till") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime till,
			@RequestParam("city") String city) {
		return bookingService.getAvailableCarsByCty(from, till, city);
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

	@PostMapping("/complete")
	public BookingCompletionResponse completeBooking(BookingCompleteRequest request) {
		return bookingService.completeBooking(request);
	}

	@PostMapping("/user/{userid}")
	public UserBookingResponse getUserBooking(@PathParam("userid") String user_id,
			@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
			@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime till) {
		return bookingService.getUserBooking(user_id, from, till);
	}

}

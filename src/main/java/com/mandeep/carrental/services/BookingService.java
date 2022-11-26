package com.mandeep.carrental.services;

import java.time.LocalDateTime;
import java.util.List;

import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.requests.BookingCancelRequest;
import com.mandeep.carrental.requests.BookingCompleteRequest;
import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingCompletionResponse;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.responses.UserBookingResponse;

public interface BookingService {

	AvailbleCarResponse getAvailableCars(LocalDateTime from, LocalDateTime till, String parkingSlotId);

	BookingResponse bookCar(BookingRequest request);

	AvailbleCarResponse getAvailableCarsByCty(LocalDateTime from, LocalDateTime till, String city);

	BookingResponse cancelRequest(BookingCancelRequest request);

	BookingResponse confirmBooking(BookingConfirmationRequest request);

	List<VehicleBookings>  getPendingBookings();

	void cancel(VehicleBookings booking);

	UserBookingResponse getUserBooking(String user_id, LocalDateTime from, LocalDateTime till);

	BookingCompletionResponse completeBooking(BookingCompleteRequest request);

	VehicleBookings getBookingForId(String bookingId);

}

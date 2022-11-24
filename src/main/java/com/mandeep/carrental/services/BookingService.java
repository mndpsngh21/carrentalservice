package com.mandeep.carrental.services;

import java.time.LocalDateTime;

import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingResponse;
import com.mandeep.carrental.responses.AvailbleCarResponse;

public interface BookingService {

	AvailbleCarResponse getAvailableCars(LocalDateTime from, LocalDateTime till, String parkingSlotId);

	BookingResponse bookCar(BookingRequest request);

}

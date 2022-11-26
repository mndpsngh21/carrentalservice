package com.mandeep.carrental.requests;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingCompleteRequest {

	String bookingId;
	LocalDateTime returnTimings;
	String dropParkingId;
	
	
	
}

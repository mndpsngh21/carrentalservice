package com.mandeep.carrental.requests;

import lombok.Data;

@Data
public class BookingConfirmationRequest {

	String bookingId;
	boolean isPaymentSuccess;
	
	
	
	
}

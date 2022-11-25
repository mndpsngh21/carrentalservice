package com.mandeep.carrental.requests;

import lombok.Data;

@Data
public class BookingCancelRequest {
	String bookingId;
	String reason;
	

}

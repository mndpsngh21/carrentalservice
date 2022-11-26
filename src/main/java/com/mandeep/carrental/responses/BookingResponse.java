package com.mandeep.carrental.responses;

import com.mandeep.carrental.entities.VehicleBookings;

import lombok.Data;

@Data
public class BookingResponse extends BaseResponse{

	VehicleBookings bookings;
	String estimatedInvoiceUrl;
	
}

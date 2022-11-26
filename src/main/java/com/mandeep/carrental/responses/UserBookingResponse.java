package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.entities.VehicleBookings;

import lombok.Data;

@Data
public class UserBookingResponse extends BaseResponse{

	List<VehicleBookings> bookings;
	
}

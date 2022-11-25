package com.mandeep.carrental;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.services.BookingService;

@Component
public class BookingStatusUpdateScheduler {

	
@Autowired	
BookingService bookingService;
	
/**
 * clear bookings those status is not confirmed within 10 minutes	
 */
@Scheduled(fixedRate = 5, timeUnit =  TimeUnit.MINUTES)
public void releasePendingBookings() {
	List<VehicleBookings> bookings= bookingService.getPendingBookings();
	LocalDateTime threshold= LocalDateTime.now().minus(5,ChronoUnit.MINUTES);
	
	for(VehicleBookings booking: bookings) {
		if(booking.getBookingDate().isBefore(threshold)) {
			bookingService.cancel(booking);
		}
	}
	
}
	
	
}

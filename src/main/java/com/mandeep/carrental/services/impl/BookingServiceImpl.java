package com.mandeep.carrental.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.ReservationStatus;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.repositories.BookingRepository;
import com.mandeep.carrental.requests.BookingCancelRequest;
import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.VehicleSearchService;
import com.mandeep.carrental.services.VehicleService;
import com.mandeep.carrental.utils.Constants;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	VehicleSearchService vehicleSearchService;
	
	@Override
	public AvailbleCarResponse getAvailableCars(LocalDateTime from, LocalDateTime till, String parkingSlotId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingResponse bookCar(BookingRequest request) {
		BookingResponse response = new BookingResponse();
		try {
			VehicleBookings booking= from(request);
		    VehicleEntity vehicle= vehicleService.getVehicleByUUID(request.getVehicleId());
		    booking.setVehicleEntity(vehicle);
		    booking.setAccountId(request.getUserId());
		    booking.setBookingDate(LocalDateTime.now(ZoneId.of("UTC")));
		    booking.setBookingFrom(request.getBookingFrom());
		    booking.setBookingTill(request.getBookingTill());
		    booking.setCreatedOn(LocalDateTime.now(ZoneId.of("UTC")));
		    booking.setBookingId(UUID.randomUUID().toString());
		    booking.setBookingStatus(ReservationStatus.PENDING.toString());
		    VehicleBookings saved=  bookingRepository.save(booking);
		    response.setBookings(saved);
		    response.createDefaultSucces(Constants.ResponseMessages.BOOKED_SUCCESS);
		} catch (InvalidInformationException e) {
			response.createDefaultError(e.getMessage());
		}
		
		return response;
	}

	private VehicleBookings from(BookingRequest request) throws InvalidInformationException {
		VehicleBookings booking= new VehicleBookings();
		booking.setBookingDate(LocalDateTime.now(ZoneId.of("UTC")));
		booking.setVehicleEntity(vehicleService.getVehicleByUUID(request.getVehicleId()));
		booking.setAccountId(request.getUserId());
		return booking;
	}

	@Override
	public AvailbleCarResponse getAvailableCarsByCty(LocalDateTime from, LocalDateTime till, String city) {
		AvailbleCarResponse availbleCarResponse= new AvailbleCarResponse();
		List<Vehicle> availableVehicles = vehicleSearchService.searchByCity(city,from,till);
		availbleCarResponse.setAvailableCar(availableVehicles);
		availbleCarResponse.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
		return availbleCarResponse;
	}

	@Override
	public BookingResponse cancelRequest(BookingCancelRequest request) {
		BookingResponse bookingResponse= new BookingResponse();
		Optional<VehicleBookings> bookings=  bookingRepository.findByBookingId(request.getBookingId());
		if(!bookings.isPresent()) {
			bookingResponse.createDefaultError("No Booking is present with this booking Id", 404);
			return bookingResponse;
		}
		VehicleBookings dbInfo=bookings.get();
		dbInfo.setBookingStatus(ReservationStatus.CANCELLED.toString());
		dbInfo.setDeletedOn(LocalDateTime.now(ZoneId.of("UTC")));
		VehicleBookings updated=  bookingRepository.save(dbInfo);
		bookingResponse.setBookings(updated);
		return bookingResponse;
	}

	@Override
	public BookingResponse confirmBooking(BookingConfirmationRequest request) {
		BookingResponse bookingResponse= new BookingResponse();
		Optional<VehicleBookings> bookings=  bookingRepository.findByBookingId(request.getBookingId());
		if(bookings.isPresent()) {
			VehicleBookings dbInfo=bookings.get();
			dbInfo.setBookingStatus(ReservationStatus.CONFIRMED.toString());
			dbInfo.setUpdatedOn(LocalDateTime.now(ZoneId.of("UTC")));
			VehicleBookings updated=  bookingRepository.save(dbInfo);
			bookingResponse.createDefaultSucces(Constants.ResponseMessages.BOOKING_CONFIRMED);
			bookingResponse.setBookings(updated);
			return bookingResponse;
		}
		else {
			// received invalid information
		}
		bookingResponse.createDefaultError("No Booking is present with this booking Id", 404);
		return bookingResponse;
	}

	@Override
	public List<VehicleBookings> getPendingBookings() {
		List<VehicleBookings> pending = bookingRepository.findByBookingStatus(ReservationStatus.PENDING.toString());
		return pending;
	}

	@Override
	public void cancel(VehicleBookings booking) {
		booking.setBookingStatus(ReservationStatus.CONFIRMED.toString());
		booking.setUpdatedOn(LocalDateTime.now(ZoneId.of("UTC")));
		VehicleBookings updated=  bookingRepository.save(booking);
	}

}

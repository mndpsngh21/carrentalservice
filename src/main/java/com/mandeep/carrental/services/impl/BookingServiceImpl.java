package com.mandeep.carrental.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.controllers.BookingController;
import com.mandeep.carrental.entities.ParkingEntity;
import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.ReservationStatus;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.repositories.BookingRepository;
import com.mandeep.carrental.requests.BookingCancelRequest;
import com.mandeep.carrental.requests.BookingCompleteRequest;
import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingCompletionResponse;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.responses.UserBookingResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.ParkingService;
import com.mandeep.carrental.services.VehicleSearchService;
import com.mandeep.carrental.services.VehicleService;
import com.mandeep.carrental.utils.Constants;

@Service
public class BookingServiceImpl implements BookingService {
	Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class); 
	
	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	VehicleSearchService vehicleSearchService;

	@Autowired
	ParkingService parkingService;

	@Value("${url.estimate}")
	String estimateURL;

	@Value("${url.final}")
	String finalURL;

	@Override
	public AvailbleCarResponse getAvailableCars(LocalDateTime from, LocalDateTime till, String parkingSlotId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingResponse bookCar(BookingRequest request) {
		BookingResponse response = new BookingResponse();
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime bookingDateTime = request.getBookingFrom();
//Validation date and time
		long diff = ChronoUnit.SECONDS.between(currentDateTime, bookingDateTime);
		if (diff <= 0) {
			logger.error("Past date time booking not allowed");
		} else {
			List<VehicleBookings> rangeBookings = bookingRepository.getBookingForDateRange(request.getBookingFrom(),
					request.getBookingTill());

			if (rangeBookings != null && rangeBookings.size() > 0) {
				Optional<VehicleBookings> vehicleBookings = rangeBookings.stream()
						.filter(b -> isSameVehicle(b, request.getVehicleId())).findAny();
				if (vehicleBookings.isPresent()) {
					logger.info("Already booking present for vehicle");
					response.createDefaultError("Already booking present for vehicle",
							Constants.ResponseCode.INVALID_REQUEST);
					return response;
				}

			}

			try {
				VehicleBookings booking = from(request);
				VehicleEntity vehicle = vehicleService.getVehicleByUUID(request.getVehicleId());
				booking.setVehicleEntity(vehicle);
				booking.setAccountId(request.getUserId());
				booking.setBookingDate(LocalDateTime.now(ZoneId.of("UTC")));
				booking.setBookingFrom(request.getBookingFrom());
				booking.setBookingTill(request.getBookingTill());
				booking.setCreatedOn(LocalDateTime.now(ZoneId.of("UTC")));
				booking.setBookingId(UUID.randomUUID().toString());
				booking.setBookingStatus(ReservationStatus.PENDING.toString());
				booking.setPickupParkingId(request.getBookingParkingId());
				VehicleBookings saved = bookingRepository.save(booking);
				response.setBookings(saved);
				response.setEstimatedInvoiceUrl(estimateURL + "/" + booking.getBookingId());
				response.createDefaultSucces(Constants.ResponseMessages.BOOKED_SUCCESS);
			} catch (InvalidInformationException e) {
				response.createDefaultError(e.getMessage());
			}
		}
		logger.info("Car booking successful");
		return response;
	}

	private boolean isSameVehicle(VehicleBookings b, String vehicleId) {
		return b.getVehicleEntity().getUuid().equalsIgnoreCase(vehicleId);
	}

	private VehicleBookings from(BookingRequest request) throws InvalidInformationException {
		VehicleBookings booking = new VehicleBookings();
		booking.setBookingDate(LocalDateTime.now(ZoneId.of("UTC")));
		booking.setVehicleEntity(vehicleService.getVehicleByUUID(request.getVehicleId()));
		booking.setAccountId(request.getUserId());
		return booking;
	}

	@Override
	public AvailbleCarResponse getAvailableCarsByCty(LocalDateTime from, LocalDateTime till, String city) {
		AvailbleCarResponse availbleCarResponse = new AvailbleCarResponse();
		List<Vehicle> availableVehicles = vehicleSearchService.searchByCity(city, from, till);
		if (!availableVehicles.isEmpty()) {
			availbleCarResponse.setAvailableCar(availableVehicles);
			availbleCarResponse.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
		}
		else {
			availbleCarResponse.createDefaultError(Constants.ResponseMessages.FAILURE);
		}
		
		return availbleCarResponse;
	}

	@Override
	public BookingResponse cancelRequest(BookingCancelRequest request) {
		BookingResponse bookingResponse = new BookingResponse();
		Optional<VehicleBookings> bookings = bookingRepository.findByBookingId(request.getBookingId());
		if (!bookings.isPresent()) {
			bookingResponse.createDefaultError("No Booking is present with this booking Id", 404);
			return bookingResponse;
		}
		VehicleBookings dbInfo = bookings.get();
		dbInfo.setBookingStatus(ReservationStatus.CANCELLED.toString());
		dbInfo.setDeletedOn(LocalDateTime.now(ZoneId.of("UTC")));
		VehicleBookings updated = bookingRepository.save(dbInfo);
		bookingResponse.setBookings(updated);
		// refunds can be handled on later stages

		return bookingResponse;
	}

	@Override
	public BookingResponse confirmBooking(BookingConfirmationRequest request) {
		BookingResponse bookingResponse = new BookingResponse();
		Optional<VehicleBookings> bookings = bookingRepository.findByBookingId(request.getBookingId());
		if (bookings.isPresent()) {
			VehicleBookings dbInfo = bookings.get();
			dbInfo.setBookingStatus(ReservationStatus.CONFIRMED.toString());
			dbInfo.setUpdatedOn(LocalDateTime.now(ZoneId.of("UTC")));
			VehicleBookings updated = bookingRepository.save(dbInfo);
			bookingResponse.createDefaultSucces(Constants.ResponseMessages.BOOKING_CONFIRMED);
			bookingResponse.setBookings(updated);
			return bookingResponse;
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
		booking.setBookingStatus(ReservationStatus.CANCELLED.toString());
		booking.setUpdatedOn(LocalDateTime.now(ZoneId.of("UTC")));
		bookingRepository.save(booking);
	}

	@Override
	public UserBookingResponse getUserBooking(String user_id, LocalDateTime from, LocalDateTime till) {
		UserBookingResponse bookingResponse = new UserBookingResponse();
		List<VehicleBookings> userBookings = bookingRepository.findByAccountIdBetweenDate(user_id, from, till);
		bookingResponse.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
		bookingResponse.setBookings(userBookings);
		return bookingResponse;
	}

	@Override
	public BookingCompletionResponse completeBooking(BookingCompleteRequest request) {
		BookingCompletionResponse response = new BookingCompletionResponse();
		Optional<VehicleBookings> bookings = bookingRepository.findByBookingId(request.getBookingId());
		ParkingEntity parking = null;
		try {
			parking = parkingService.getParkingById(request.getDropParkingId());
		} catch (InvalidInformationException e) {
			response.createDefaultError("Drop location should be a valid parking location", 404);
			return response;
		}

		if (bookings.isPresent()) {
			VehicleBookings dbInfo = bookings.get();
			dbInfo.setBookingStatus(ReservationStatus.COMPLETED.toString());
			dbInfo.setReturnOn(request.getReturnTimings());
			vehicleService.updateVehicleCurrentLocation(dbInfo.getVehicleEntity().getId(), parking.getLatitude(),
					parking.getLongitude());
			bookingRepository.save(dbInfo);
			response.createDefaultSucces(Constants.ResponseMessages.BOOKING_CONFIRMED);
			response.setFinalInvoiceUrl(finalURL + "/" + request.getBookingId());
			return response;
		}
		response.createDefaultError("No Booking is present with this booking Id", 404);
		return response;
	}

	@Override
	public VehicleBookings getBookingForId(String bookingId) {
		Optional<VehicleBookings> bookings = bookingRepository.findByBookingId(bookingId);
		return bookings.get();
	}

}

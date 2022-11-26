package com.mandeep.carrental;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mandeep.carrental.models.User;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.model.Parking;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.responses.UserBookingResponse;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.ParkingService;
import com.mandeep.carrental.services.VehicleService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BookingServiceTest {

	@Autowired
	BookingService bookingService;

	@Autowired
	TestCaseUtils testCaseUtils;

	User testUser;

	Vehicle testVehicle;

	Parking testParking;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	ParkingService parkingService;

	@BeforeAll
	public void setUp() {
		UserAccountResponse accountResponse = testCaseUtils.addTestUser();
		testUser = accountResponse.getUser();
		VehicleListResponse vehiclesResponse = vehicleService.getAllVehicles();
		List<Vehicle> vehicles = vehiclesResponse.getVehicles();
		testVehicle = vehicles.get(0);
		testParking = parkingService.getAllParkings().getParkings().get(0);
	}

	public BookingRequest createBookingRequest(int start, int end) {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setUserId(testUser.getId());
		bookingRequest.setVehicleId(testVehicle.getCar_id());

		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime nextDate = localDateTime.plusDays(start);
		LocalDateTime tillDate = localDateTime.plusDays(end);

		bookingRequest.setBookingFrom(nextDate);
		bookingRequest.setBookingTill(tillDate);
		bookingRequest.setBookingParkingId(testParking.getId());
		return bookingRequest;
	}

	@Test
	public void testBookingForUser() {
		BookingResponse bookingResponse = bookingService.bookCar(createBookingRequest(1, 3));
		assertTrue(bookingResponse.isSuccess());
	}

	@Test
	public void sameDateBookingNotAllowed() {
		BookingResponse bookingResponse = bookingService.bookCar(createBookingRequest(1, 3));
		assertEquals(false,bookingResponse.isSuccess());
	}

	
	@Test
	public void bookingShouldHaveEstimateURL() {
		BookingResponse bookingResponse = bookingService.bookCar(createBookingRequest(4, 5));
		assertNotNull(bookingResponse.getEstimatedInvoiceUrl());
	}
	
	@Test
	public void bookingShouldbeAvailableForUser() {
		BookingRequest bookingrequest=createBookingRequest(7, 8);
		BookingResponse bookingResponse = bookingService.bookCar(bookingrequest);
		assertNotNull(bookingResponse.getEstimatedInvoiceUrl());
		// check if bookings available for user
		UserBookingResponse userBookingResponse = bookingService.getUserBooking(bookingrequest.getUserId(), bookingrequest.getBookingFrom(), bookingrequest.getBookingTill());
		assertEquals(1,userBookingResponse.getBookings().size());
	}

}

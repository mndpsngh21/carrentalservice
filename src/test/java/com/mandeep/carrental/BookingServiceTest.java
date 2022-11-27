package com.mandeep.carrental;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mandeep.carrental.models.User;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.requests.BookingCancelRequest;
import com.mandeep.carrental.requests.BookingCompleteRequest;
import com.mandeep.carrental.requests.BookingConfirmationRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.BookingCompletionResponse;
import com.mandeep.carrental.response.model.Parking;
import com.mandeep.carrental.responses.AvailbleCarResponse;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.responses.UserBookingResponse;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.ParkingService;
import com.mandeep.carrental.services.VehicleSearchService;
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

	private LocalDateTime from;

	private LocalDateTime till;
	
	private Vehicle vehicle;

	@MockBean
	BookingConfirmationRequest bookingConfirmationRequest;

	@MockBean
	BookingCancelRequest bookingCancelRequest;

	@MockBean
	BookingCompleteRequest bookingCompleteRequest;
	@MockBean
	VehicleSearchService vehicleSearchService;

	@BeforeAll
	public void setUp() {
		UserAccountResponse accountResponse = testCaseUtils.addTestUser();
		testUser = accountResponse.getUser();
		VehicleListResponse vehiclesResponse = vehicleService.getAllVehicles();
		List<Vehicle> vehicles = vehiclesResponse.getVehicles();
		testVehicle = vehicles.get(0);
		testParking = parkingService.getAllParkings().getParkings().get(0);
		LocalDateTime localDateTime1 = LocalDateTime.now();
		from = localDateTime1.plusDays(1);
		till = localDateTime1.plusDays(5);

		// create dummy vehicle
		Vehicle vehicle = new Vehicle();
		vehicle.setCar_id("1");
		vehicle.setModel("SUV");
		vehicle.setRegisterationNumber("12345");
		vehicle.setImageUrl("www.car.com");
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
		assertEquals(false, bookingResponse.isSuccess());
	}

	@Test
	public void bookingShouldHaveEstimateURL() {
		BookingResponse bookingResponse = bookingService.bookCar(createBookingRequest(4, 5));
		assertNotNull(bookingResponse.getEstimatedInvoiceUrl());
	}

	@Test
	public void bookingShouldbeAvailableForUser() {
		BookingRequest bookingrequest = createBookingRequest(7, 8);
		BookingResponse bookingResponse = bookingService.bookCar(bookingrequest);
		assertNotNull(bookingResponse.getEstimatedInvoiceUrl());
		// check if bookings available for user
		UserBookingResponse userBookingResponse = bookingService.getUserBooking(bookingrequest.getUserId(),
				bookingrequest.getBookingFrom(), bookingrequest.getBookingTill());
		assertEquals(1, userBookingResponse.getBookings().size());
	}

	@Test
	public void testGetAvailableCarsByCty() {
		AvailbleCarResponse availbleCarResponse = bookingService.getAvailableCarsByCty(from, till, "Ajmer");
		assertFalse(availbleCarResponse.isSuccess());
	}
	
	@Test
	public void testgetAvailableCarsByCtyPositive() {
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(vehicle);
		Mockito.when(vehicleSearchService.searchByCity(any(), any(), any())).thenReturn(vehicleList);
		AvailbleCarResponse availbleCarResponse = bookingService.getAvailableCarsByCty(from, till, "Ajmer");
		assertTrue(availbleCarResponse.isSuccess());
	}

	@Test
	public void testConfirmBooking() {
		Mockito.when(bookingConfirmationRequest.getBookingId()).thenReturn("1");
		BookingResponse bookingResponse = bookingService.confirmBooking(bookingConfirmationRequest);
		assertFalse(bookingResponse.isSuccess());
	}

	@Test
	public void testCancelRequest() {
		Mockito.when(bookingCancelRequest.getBookingId()).thenReturn("1");
		BookingResponse bookingResponse = bookingService.cancelRequest(bookingCancelRequest);
		assertFalse(bookingResponse.isSuccess());

	}

	@Test
	public void testCompleteBooking() {
		Mockito.when(bookingCompleteRequest.getBookingId()).thenReturn("1");
		Mockito.when(bookingCompleteRequest.getDropParkingId()).thenReturn("11");

		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(vehicle);
		Mockito.when(vehicleSearchService.searchByCity(any(), any(), any())).thenReturn(vehicleList);
		BookingCompletionResponse bookingResponse = bookingService.completeBooking(bookingCompleteRequest);
		assertFalse(bookingResponse.isSuccess());
	}
	
	@Test
	public void testconfirmBooking() {
		
	}

}

package com.mandeep.carrental.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mandeep.carrental.TestCaseUtils;
import com.mandeep.carrental.models.Invoice;
import com.mandeep.carrental.models.User;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.requests.BookingCompleteRequest;
import com.mandeep.carrental.requests.BookingRequest;
import com.mandeep.carrental.response.model.Charges;
import com.mandeep.carrental.response.model.Parking;
import com.mandeep.carrental.responses.BookingResponse;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.services.impl.DayInvoiceService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
class InvoiceServiceTest {

	@Autowired
	BookingService bookingService;

	@Autowired
	TestCaseUtils testCaseUtils;

	User testUser;

	Vehicle testVehicle;

	Parking testParking;
	BookingRequest bookingRequest ;
	BookingResponse bookingResponse;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	ParkingService parkingService;
	
	@Autowired
	InvoiceService invoiceService;

	@BeforeAll
	public void setUp() {
		UserAccountResponse accountResponse = testCaseUtils.addTestUser();
		testUser = accountResponse.getUser();
		VehicleListResponse vehiclesResponse = vehicleService.getAllVehicles();
		List<Vehicle> vehicles = vehiclesResponse.getVehicles();
		testVehicle = vehicles.get(0);
		testParking = parkingService.getAllParkings().getParkings().get(0);
		bookingRequest =createBookingRequest(10, 15);
		bookingResponse = bookingService.bookCar(bookingRequest);

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
	void testComputeInvoice() {
		BookingCompleteRequest bookingCompleteRequest= new BookingCompleteRequest();
		bookingCompleteRequest.setBookingId(bookingResponse.getBookings().getBookingId());
		bookingCompleteRequest.setDropParkingId(testParking.getId());
		bookingCompleteRequest.setReturnTimings(bookingRequest.getBookingTill().minusDays(1));
		bookingService.completeBooking(bookingCompleteRequest);
		Assert.assertTrue(bookingResponse.isSuccess());
		Invoice invoice = invoiceService.computeInvoice(bookingResponse.getBookings().getBookingId(), true);
		Assert.assertNotNull(invoice);
		Charges charges= testVehicle.getCharges();
		double dailycharges = charges.getDailyCharges();
		Duration rentedDuration =Duration.between(bookingRequest.getBookingFrom(),bookingRequest.getBookingTill().minusDays(1));
		double amount = rentedDuration.toDays()*dailycharges;
		amount+= amount*DayInvoiceService.TAX_PERCENTAGE;
		Assert.assertEquals(amount, invoice.getTotal(),0.0001);
	}

	@Test
	void testGetEstimatedInvoice() {
		BookingRequest bookingRequest =createBookingRequest(1, 3);
		BookingResponse bookingResponse = bookingService.bookCar(bookingRequest);
		Assert.assertTrue(bookingResponse.isSuccess());
		Invoice invoice = invoiceService.computeInvoice(bookingResponse.getBookings().getBookingId(), false);
		Assert.assertNotNull(invoice);
		Charges charges= testVehicle.getCharges();
		double dailycharges = charges.getDailyCharges();
		Duration rentedDuration =Duration.between(bookingRequest.getBookingFrom(),bookingRequest.getBookingTill());
		double amount = rentedDuration.toDays()*dailycharges;
		amount+= amount*DayInvoiceService.TAX_PERCENTAGE;
		Assert.assertEquals(amount, invoice.getTotal(),0.0001);
	}

}

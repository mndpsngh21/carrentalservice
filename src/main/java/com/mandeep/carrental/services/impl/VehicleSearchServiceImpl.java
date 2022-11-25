package com.mandeep.carrental.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.models.ReservationStatus;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.models.VehicleType;
import com.mandeep.carrental.repositories.BookingRepository;
import com.mandeep.carrental.services.VehicleSearchService;
import com.mandeep.carrental.services.VehicleService;

@Service
public class VehicleSearchServiceImpl implements VehicleSearchService {

	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	BookingRepository bookingRepository;

	@Override
	public List<Vehicle> searchByCity(VehicleType vehicleType, String city, LocalDateTime fromDate,
			LocalDateTime toDate) {

		/**
		 * Read all vehicles for city
		 */
		List<Vehicle> cityVehicles =vehicleService.getAllVehicles().getVehicles().stream()
				.filter(vehilce -> vehilce.getVehicleType().equals(vehicleType)
						&& vehilce.getParkingLocation().getCity().equalsIgnoreCase(city))
				.collect(Collectors.toList());

		
		List<VehicleBookings> bookings = getBookingForDateRange(fromDate, toDate);
		/**
		 *  If vehicles present then check vehicles availability for desired timings
		 */
		if (cityVehicles != null) {
			List<Vehicle> availableVehicles = cityVehicles.stream().filter(v -> isVehicleAvailable(v, fromDate, toDate,bookings ))
					.collect(Collectors.toList());
			return availableVehicles;
		}

		return new ArrayList<Vehicle>();
	}
	
	@Override
	public List<Vehicle> searchByCity(String city, LocalDateTime fromDate,
			LocalDateTime toDate) {

		/**
		 * Read all vehicles for city
		 */
		List<Vehicle> cityVehicles =vehicleService.getAllVehicles().getVehicles().stream()
				.filter(vehilce -> vehilce.getParkingLocation().getCity().equalsIgnoreCase(city))
				.collect(Collectors.toList());

		
		List<VehicleBookings> bookings = getBookingForDateRange(fromDate, toDate);
		/**
		 *  If vehicles present then check vehicles availability for desired timings
		 */
		if (cityVehicles != null) {
			List<Vehicle> availableVehicles = cityVehicles.stream().filter(v -> isVehicleAvailable(v, fromDate, toDate,bookings ))
					.collect(Collectors.toList());
			return availableVehicles;
		}

		return new ArrayList<Vehicle>();
	}

	private boolean isVehicleAvailable(Vehicle v, LocalDateTime fromDate, LocalDateTime toDate, List<VehicleBookings> bookings) {
		
		for(VehicleBookings b: bookings) {
			if(b.getVehicleEntity().getUuid().equals(v.getCar_id())) {
				if(b.getBookingStatus().equalsIgnoreCase(ReservationStatus.PENDING.toString())||b.getBookingStatus().equalsIgnoreCase(ReservationStatus.CONFIRMED.toString())||b.getBookingStatus().equalsIgnoreCase(ReservationStatus.ACTIVE.toString())) {
					return false;
				}
			}			
		}
		return true;
	}

	private List<VehicleBookings> getBookingForDateRange(LocalDateTime from,LocalDateTime till) {
		return bookingRepository.getBookingForDateRange(from,till);
	}
	
	@Override
	public List<Vehicle> searchByParkingLot(String parkinglotId, String model, LocalDateTime fromDate,
			LocalDateTime toDate) {

		return null;
	}

}

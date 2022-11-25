package com.mandeep.carrental.services;

import java.time.LocalDateTime;
import java.util.List;

import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.models.VehicleType;

public interface VehicleSearchService {
	List<Vehicle> searchByCity(VehicleType vehicleType, String city, LocalDateTime fromDate, LocalDateTime toDate);
	List<Vehicle> searchByParkingLot(String parkinglotId, String model, LocalDateTime fromDate,LocalDateTime toDate);
	List<Vehicle> searchByCity(String city, LocalDateTime fromDate, LocalDateTime toDate);
}

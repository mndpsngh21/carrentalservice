package com.mandeep.carrental.services;

import java.util.List;

import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.exceptions.VehicleNotFoundException;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.responses.VehicleResponse;

public interface VehicleService {

	  VehicleListResponse getAllVehicles();	
	
	  VehicleResponse getVehicleById(String id);

	  VehicleResponse addVehicle(Vehicle vehicle);

	  VehicleResponse removeVehicle(String vehicleId);
	  
	  void addDefaultVehicles();
}

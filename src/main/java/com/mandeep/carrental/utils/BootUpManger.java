package com.mandeep.carrental.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mandeep.carrental.services.ChargesService;
import com.mandeep.carrental.services.ParkingService;
import com.mandeep.carrental.services.VehicleService;
import com.mandeep.carrental.services.VehicleStatusService;
import com.mandeep.carrental.services.VehicleTypeService;

@Component
public class BootUpManger {

	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@Autowired
	VehicleStatusService vehicleStatusService;

	@Autowired
	ChargesService chargesService;

	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	ParkingService parkingService;
	
	public void init() {
		parkingService.addDefaultParkings();
		vehicleTypeService.addDefaultVehilceTypes();
		vehicleStatusService.setDefaultStatus();
		chargesService.setDefaultCharges();
		vehicleService.addDefaultVehicles();
	}
	
	
}

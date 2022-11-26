package com.mandeep.carrental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.responses.VehicleStatusResponse;
import com.mandeep.carrental.services.VehicleStatusService;

@RestController
@RequestMapping("/vehiclestatus")
@CrossOrigin
public class VehicleStatusController {

	
	@Autowired
	VehicleStatusService vehicleStatusService;
	
	@GetMapping("/all")
	public VehicleStatusResponse getAllStatus() {
		return vehicleStatusService.getAllStatuses();
	}
	
}

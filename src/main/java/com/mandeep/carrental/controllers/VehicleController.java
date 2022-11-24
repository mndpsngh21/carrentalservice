package com.mandeep.carrental.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.responses.VehicleResponse;
import com.mandeep.carrental.services.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	VehicleService vehicleService;
	
	@GetMapping("/all")
	public VehicleListResponse getAllVehicles() {
		return vehicleService.getAllVehicles();
	}
	
	@PostMapping("/add")
	public VehicleResponse addVehicle(@RequestBody Vehicle vehicle) {
		return vehicleService.addVehicle(vehicle);
	}
	
	@PostMapping("/update")
	public VehicleResponse updateVehicle(@RequestBody Vehicle vehicle) {
		return vehicleService.addVehicle(vehicle);
	}
	
	@PostMapping("/delete/{id}")
	public VehicleResponse deleteVehicle(@PathParam("id") String id) {
		return vehicleService.removeVehicle(id);
	}
	
}

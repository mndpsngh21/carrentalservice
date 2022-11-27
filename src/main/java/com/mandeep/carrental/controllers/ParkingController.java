package com.mandeep.carrental.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.requests.ParkingRequest;
import com.mandeep.carrental.responses.AddParkingResponse;
import com.mandeep.carrental.responses.ParkingResponse;
import com.mandeep.carrental.services.ParkingService;

@RestController
@RequestMapping("/parking")
@CrossOrigin
public class ParkingController {
	Logger logger = LoggerFactory.getLogger(ParkingController.class);
	@Autowired
	ParkingService parkingService;
	
	@GetMapping("/all")
	public ParkingResponse getParkings() {
		return parkingService.getAllParkings();
	}
	
	@PostMapping("/add")
	public AddParkingResponse addParking(@RequestBody ParkingRequest request) {
		return parkingService.addParking(request);
	}
	
	@PostMapping("/update")
	public AddParkingResponse updateParking(@RequestBody ParkingRequest request) {
		return parkingService.updateParking(request);
	}
	
	@PostMapping("/delete/{id}")
	public AddParkingResponse deleteParking(@RequestParam("id") String id) {
		return parkingService.deleteParking(id);
	}
	
}

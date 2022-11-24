package com.mandeep.carrental.models;

import com.mandeep.carrental.response.model.Charges;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {

	private String registerationNumber;
	private String car_id;
	private String modelInformation;
	private int numberOfSeats;
	private String make;
	private String model;
	private String imageUrl;
	private double mileage;
	private Charges charges;
	private Location parkingLocation;
	private VehicleType vehicleType;
	private VehicleStatus vehicleStatus;
	
}

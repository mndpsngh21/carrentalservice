package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.exceptions.VehicleNotFoundException;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.models.VehicleStatus;
import com.mandeep.carrental.repositories.ChargesRepository;
import com.mandeep.carrental.repositories.VehicleRepository;
import com.mandeep.carrental.response.model.Charges;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.responses.VehicleResponse;
import com.mandeep.carrental.services.ChargesService;
import com.mandeep.carrental.services.VehicleService;
import com.mandeep.carrental.services.VehicleStatusService;
import com.mandeep.carrental.services.VehicleTypeService;

@Service
public class VehicleServiceImpl implements VehicleService{

	@Autowired
	VehicleRepository vehicleRepository;
	
	
	@Autowired
	ChargesService chargesService;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@Autowired
	VehicleStatusService vehicleStatusService;
	
	@Override
	public VehicleListResponse getAllVehicles(){
	//	vehicleRepository.findAll().stream().map(t->from(t)).collect(Collectors.toList());
		
		List<Vehicle> vehicles=  vehicleRepository.findAll().stream().map(t->from(t)).collect(Collectors.toList());
		
		
		return null;
	}

	@Override
	public VehicleResponse getVehicleById(String id) {
		return null;
	}

	@Override
	public VehicleResponse addVehicle(Vehicle vehicle) {
		return null;
	}

	@Override
	public VehicleResponse removeVehicle(String vehicleId){
		return null;
	}

	Vehicle from(VehicleEntity v) {
		Vehicle vehicle= new Vehicle();
		vehicle.setCar_id(v.getUuid());
		Charges charges = null;
		try {
			charges = chargesService.getChargeService(v.getCharges().getId());
		} catch (InvalidInformationException e) {
			e.printStackTrace();
		}
		vehicle.setCharges(charges);
		vehicle.setVehicleType(vehicleTypeService.from(v.getVehicleType()));
		vehicle.setRegisterationNumber(v.getRegisterationNumber());
		vehicle.setMake(v.getMake());
		vehicle.setMileage(v.getMileage());
		vehicle.setModel(v.getModel());
		vehicle.setNumberOfSeats(v.getNumberOfSeats());
		vehicle.setVehicleStatus(vehicleStatusService.from(v.getVehicleStatus()));
		return vehicle;
     } 
	
	@Override
	public void addDefaultVehicles() {
		VehicleEntity vehicle1= createVehicle("Toyota Camry", "ABC11","Toyota", 7, 4,12.4545d, 45.4554d);
		VehicleEntity vehicle2= createVehicle("Toyota Camry", "ABC12","Toyota", 7, 4,12.4545d, 45.4554d);
		VehicleEntity vehicle3= createVehicle("BMW 650", "ZBC12","BMW", 5, 4,12.4545d, 45.4554d);
		VehicleEntity vehicle4= createVehicle("BMW 650", "ZBC15","BMW", 5, 4,12.4545d, 45.4554d);
		vehicleRepository.save(vehicle1);
		vehicleRepository.save(vehicle2);
		vehicleRepository.save(vehicle3);
		vehicleRepository.save(vehicle4);
		
	}
	
	VehicleEntity createVehicle(String model, String registeredNumber, String make, double mileage, int seats, double latitude, double longitude) {
		VehicleEntity vehicle= new VehicleEntity();
		vehicle.setModel(model);
		vehicle.setRegisterationNumber(registeredNumber);
		vehicle.setMake(make);
		vehicle.setMileage(mileage);
		vehicle.setNumberOfSeats(seats);
		vehicle.setParkingLatitude(latitude);
		vehicle.setParkingLongitude(longitude);
		return vehicle;
	}

}

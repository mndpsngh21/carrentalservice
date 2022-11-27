package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.ChargesEntity;
import com.mandeep.carrental.entities.VehiceStatusEntity;
import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.entities.VehicleTypeEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.Vehicle;
import com.mandeep.carrental.repositories.VehicleRepository;
import com.mandeep.carrental.response.model.Charges;
import com.mandeep.carrental.responses.VehicleListResponse;
import com.mandeep.carrental.responses.VehicleResponse;
import com.mandeep.carrental.services.ChargesService;
import com.mandeep.carrental.services.ParkingService;
import com.mandeep.carrental.services.VehicleService;
import com.mandeep.carrental.services.VehicleStatusService;
import com.mandeep.carrental.services.VehicleTypeService;
import com.mandeep.carrental.utils.Constants;
import com.mandeep.carrental.utils.ImagesUtil;

@Service
public class VehicleServiceImpl implements VehicleService {
	Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	ChargesService chargesService;

	@Autowired
	VehicleTypeService vehicleTypeService;

	@Autowired
	VehicleStatusService vehicleStatusService;
	
	
	@Autowired
	ParkingService parkingService;

	
	@Autowired
	ImagesUtil imagesUtil;
	
	@Override
	public VehicleListResponse getAllVehicles() {
		VehicleListResponse response = new VehicleListResponse();
		List<Vehicle> vehicles = vehicleRepository.findAll().stream().map(t -> from(t)).collect(Collectors.toList());
		response.setVehicles(vehicles);
		response.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
		return response;
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
	public VehicleResponse removeVehicle(String vehicleId) {
		return null;
	}

	Vehicle from(VehicleEntity v) {
		Vehicle vehicle = new Vehicle();
		vehicle.setCar_id(v.getUuid());
		Charges charges = null;
		try {
			charges = chargesService.getChargeService(v.getCharges().getId());
			vehicle.setCar_id(v.getUuid());
			vehicle.setCharges(charges);
			vehicle.setVehicleType(vehicleTypeService.from(v.getVehicleType()));
			vehicle.setRegisterationNumber(v.getRegisterationNumber());
			vehicle.setMake(v.getMake());
			vehicle.setMileage(v.getMileage());
			vehicle.setModel(v.getModel());
			vehicle.setImageUrl(imagesUtil.getImageUrl(v.getModel()));
			vehicle.setNumberOfSeats(v.getNumberOfSeats());
			vehicle.setParkingLocation(parkingService.getParkingByLocation(v.getParkingLatitude(),v.getParkingLongitude()));
			vehicle.setNumberOfSeats(v.getNumberOfSeats());
			vehicle.setVehicleStatus(vehicleStatusService.from(v.getVehicleStatus()));
		
		} catch (InvalidInformationException e) {
			e.printStackTrace();
		}
		return vehicle;
	}

	@Override
	public void addDefaultVehicles() {
		try {
			VehicleEntity vehicle1 = createVehicle("Toyota Camry", "ABC11", "Toyota", 7, 4, Constants.Parkings.location_1.getLatitude(), Constants.Parkings.location_1.getLongitude());
			VehicleEntity vehicle2 = createVehicle("Toyota Camry", "ABC12", "Toyota", 7, 4, Constants.Parkings.location_1.getLatitude(), Constants.Parkings.location_1.getLongitude());
			VehicleEntity vehicle3 = createVehicle("BMW 650", "ZBC12", "BMW", 5, 4, Constants.Parkings.location_1.getLatitude(), Constants.Parkings.location_1.getLongitude());
			VehicleEntity vehicle4 = createVehicle("BMW 650", "ZBC15", "BMW", 5, 4, Constants.Parkings.location_1.getLatitude(), Constants.Parkings.location_1.getLongitude());
			vehicleRepository.save(vehicle1);
			vehicleRepository.save(vehicle2);
			vehicleRepository.save(vehicle3);
			vehicleRepository.save(vehicle4);
		} catch (InvalidInformationException e) {
			e.printStackTrace();
		}

	}

	VehicleEntity createVehicle(String model, String registeredNumber, String make, double mileage, int seats,
			double latitude, double longitude) throws InvalidInformationException {
		VehicleEntity vehicle = new VehicleEntity();
		vehicle.setUuid(UUID.randomUUID().toString());
		vehicle.setModel(model);
		vehicle.setRegisterationNumber(registeredNumber);
		vehicle.setMake(make);
		vehicle.setMileage(mileage);
		vehicle.setNumberOfSeats(seats);
		vehicle.setParkingLatitude(latitude);
		vehicle.setParkingLongitude(longitude);
		// charges
		vehicle.setCharges(getDefaultCharges());
		// status
		vehicle.setVehicleStatus(getDefaultStatus());
		// type
		vehicle.setVehicleType(getDefaultType());

		return vehicle;
	}

	private VehicleTypeEntity getDefaultType() throws InvalidInformationException {

		return vehicleTypeService.getVehicleTypeById(1l);
	}

	private VehiceStatusEntity getDefaultStatus() throws InvalidInformationException {
		return vehicleStatusService.getStatusById(1l);
	}

	private ChargesEntity getDefaultCharges() throws InvalidInformationException {
		return chargesService.getChargesById(1l);
	}

	@Override
	public VehicleEntity getVehicleByUUID(String uuid) throws InvalidInformationException {
		Optional<VehicleEntity> vehicleEntity=  vehicleRepository.findByUuid(uuid);
		if(!vehicleEntity.isPresent()) {
			throw new InvalidInformationException("Vehicle with given uuid not present");
		}
		return vehicleEntity.get();
	}

	@Override
	public void updateVehicleCurrentLocation(Long vehicleId, Double latitude, Double longitude) {
		Optional<VehicleEntity> vehicleEntity=  vehicleRepository.findById(vehicleId);
		if(vehicleEntity.isPresent()) {
		  VehicleEntity entity = vehicleEntity.get();
		  entity.setParkingLatitude(latitude);
		  entity.setParkingLongitude(longitude);
		  vehicleRepository.save(entity);
		}
	}

}

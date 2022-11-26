package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.ParkingEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.Location;
import com.mandeep.carrental.repositories.ParkingRepository;
import com.mandeep.carrental.requests.ParkingRequest;
import com.mandeep.carrental.response.model.Parking;
import com.mandeep.carrental.responses.AddParkingResponse;
import com.mandeep.carrental.responses.ParkingResponse;
import com.mandeep.carrental.services.ParkingService;
import com.mandeep.carrental.utils.Constants;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	ParkingRepository repository;
	
	@Override
	public ParkingResponse getAllParkings() {
		ParkingResponse response= new ParkingResponse();
		List<ParkingEntity> entities= repository.findAll();
		List<Parking> parkings = entities.stream().map(t->from(t)).collect(Collectors.toList());
		response.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
		response.setParkings(parkings);
		return response;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public AddParkingResponse addParking(ParkingRequest request) {
		AddParkingResponse response= new AddParkingResponse();
		
		try {
			ParkingEntity entity= from(request);
			ParkingEntity saved=repository.save(entity);
			Parking parking= from(saved);
			response.setParking(parking);	
			response.createDefaultSucces(Constants.ResponseMessages.SAVED_PARKING);
		}
		catch (Exception e) {
			response.createDefaultSucces(Constants.ResponseMessages.FAILED_SAVED_PARKING);
		}
		return response;
	}

	/**
	 * 
	 * Convert entity to object
	 * 
	 * @param saved
	 * @return
	 */
	private Parking from(ParkingEntity saved) {
		Parking entity= new Parking();
		entity.setAddress(saved.getAddress());
		entity.setCity(saved.getCity());
		entity.setId(saved.getUuid());
		Location location = new Location();
		location.setLatitude(saved.getLatitude());
		location.setLongitude(saved.getLongitude());
		entity.setLocation(location);
		return entity;
	}

	/**
	 * 
	 * Convert object to entity
	 * 
	 * @param saved
	 * @return
	 */

	private ParkingEntity from(ParkingRequest request) {
		ParkingEntity entity= new ParkingEntity();
		entity.setAddress(request.getAddress());
		entity.setCity(request.getCity());
		entity.setUuid(UUID.randomUUID().toString());
		entity.setLatitude(request.getLocation().getLatitude());
		entity.setLongitude(request.getLocation().getLongitude());
		return entity;
	}

	@Override
	public AddParkingResponse deleteParking(String id) {
		AddParkingResponse response= new AddParkingResponse();
		 
		Optional<ParkingEntity> optional=  repository.findByUuid(id);
		if(!optional.isPresent()) {
			response.createDefaultError("Record not found", 404);
		}
		// delete records
		repository.deleteById(optional.get().getId());		
		response.createDefaultSucces(Constants.ResponseMessages.DELETE_SUCCESS);		
		return response;
	}

	@Override
	public AddParkingResponse updateParking(ParkingRequest request) {
		AddParkingResponse response= new AddParkingResponse();
		 
		Optional<ParkingEntity> optional=  repository.findByUuid(request.getId());
		if(!optional.isPresent()) {
			response.createDefaultError("Record not found", 404);
		}
		ParkingEntity parkingEntity=	optional.get();
		ParkingEntity updatedValue = from(request);
		updatedValue.setId(parkingEntity.getId());
		try {
			ParkingEntity saved=repository.save(updatedValue);
			response.setParking(from(saved));	
			response.createDefaultSucces(Constants.ResponseMessages.SAVED_PARKING);
		}
		catch (Exception e) {
			response.createDefaultSucces(Constants.ResponseMessages.FAILED_SAVED_PARKING);
		}
		return response;
	}
	@Override
	public void addDefaultParkings() {
		Location location1= Constants.Parkings.location_1;
		Location location2= Constants.Parkings.location_2;
		Location location3= Constants.Parkings.location_3;

		addParking(createParkingRequest("Parking Address 1","Bangalore",location1.getLatitude(),location1.getLongitude()));
		addParking(createParkingRequest("Parking Address 2","Bangalore",location2.getLatitude(),location2.getLongitude()));
		addParking(createParkingRequest("Parking Address 3","Bangalore",location3.getLatitude(),location3.getLongitude()));
	}
	
	ParkingRequest createParkingRequest(String address,String city, double lat, double lng) {
		ParkingRequest parkingRequest= new ParkingRequest();
		parkingRequest.setLocation(new Location(lat,lng));
		parkingRequest.setCity(city);
		parkingRequest.setAddress(address);
		return parkingRequest;
	}
	@Override
	public Parking getParkingByLocation(double parkingLatitude, double parkingLongitude) throws InvalidInformationException {
		Optional<ParkingEntity> optional=  repository.findByLocation(parkingLatitude,parkingLongitude);
		if(!optional.isPresent()) {
			throw new InvalidInformationException("No Information found for Parking Location");
		}
		return from(optional.get());
	}
	@Override
	public ParkingEntity getParkingById(String parkingId) throws InvalidInformationException {
		Optional<ParkingEntity> optional=  repository.findByUuid(parkingId);
		if(!optional.isPresent()) {
			throw new InvalidInformationException("No Information found for Parking Location");
		}
		return optional.get();
	}
	
	
	
	
	

}

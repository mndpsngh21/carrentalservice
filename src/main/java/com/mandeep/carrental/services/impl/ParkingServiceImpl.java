package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.ParkingEntity;
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
		entity.setId(UUID.randomUUID().toString());
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
	
	
	
	

}

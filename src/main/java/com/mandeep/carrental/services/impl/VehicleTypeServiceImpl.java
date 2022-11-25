package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.VehicleTypeEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.VehicleType;
import com.mandeep.carrental.repositories.VehicleTypeRepository;
import com.mandeep.carrental.responses.VehicleTypeResponse;
import com.mandeep.carrental.services.VehicleTypeService;


@Service
public class VehicleTypeServiceImpl implements VehicleTypeService{

	@Autowired
	VehicleTypeRepository repository;
	
	@Override
	public VehicleTypeResponse getVehicleTypes() {
		VehicleTypeResponse response= new VehicleTypeResponse();
		List<VehicleType> vehicleTypes=  repository.findAll().stream().map(t->from(t)).collect(Collectors.toList());
		response.setVehicleType(vehicleTypes);
		return response;
	}
	@Override
	public VehicleType from(VehicleTypeEntity t) {
		return VehicleType.valueOf(t.getType());
	}

	@Override
	public void addDefaultVehilceTypes() {
		for(VehicleType type:VehicleType.values()) {
			repository.save(from(type));
		}
	}
	@Override
	public VehicleTypeEntity from(VehicleType type) {
		VehicleTypeEntity entity= new VehicleTypeEntity();
		entity.setType(type.toString());
		return entity;
	}

	@Override
	public VehicleType findById(Long id) {
		Optional<VehicleTypeEntity> optional= repository.findById(id);
		return from(optional.get());
	}
	@Override
	public VehicleTypeEntity getVehicleTypeById(long id) throws InvalidInformationException {
		Optional<VehicleTypeEntity> optional= repository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidInformationException("Vehilce Type is not present");
		}
		
		return optional.get();
	}
	
	
	

}

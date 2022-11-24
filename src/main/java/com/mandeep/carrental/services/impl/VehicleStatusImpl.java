package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.VehiceStatusEntity;
import com.mandeep.carrental.models.VehicleStatus;
import com.mandeep.carrental.repositories.VehicleStatusRepository;
import com.mandeep.carrental.responses.VehicleStatusResponse;
import com.mandeep.carrental.services.VehicleStatusService;
import com.mandeep.carrental.utils.Constants;

@Service
public class VehicleStatusImpl implements VehicleStatusService {

	@Autowired
	VehicleStatusRepository repository;
	
	@Override
	public VehicleStatusResponse getVehicleStatus() {
		VehicleStatusResponse response= new VehicleStatusResponse();
		List<VehicleStatus> statuses=  repository.findAll().stream().map(t->from(t)).collect(Collectors.toList());
		response.createDefaultSucces(Constants.ResponseMessages.DELETE_SUCCESS);
		response.setStatus(statuses);
		return response;
	}

	/**
	 * This method set default data
	 */
	@Override
	public void setDefaultStatus() {
		for( VehicleStatus status:VehicleStatus.values()) {
			repository.save(from(status));
		}
	}
	
	@Override
	public VehicleStatus from(VehiceStatusEntity entity) {
	 return VehicleStatus.valueOf(entity.getStatus());
	}
	
	@Override
	public VehiceStatusEntity from(VehicleStatus vehicleStatus) {
		VehiceStatusEntity entity= new VehiceStatusEntity();
		entity.setStatus(vehicleStatus.toString());
		return entity;
   }

}

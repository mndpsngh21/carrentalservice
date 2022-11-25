package com.mandeep.carrental.services;

import com.mandeep.carrental.entities.VehicleTypeEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.VehicleType;
import com.mandeep.carrental.responses.VehicleTypeResponse;

public interface VehicleTypeService {

	VehicleTypeResponse getVehicleTypes();
	
	VehicleType findById(Long id);
	
	void addDefaultVehilceTypes();
	VehicleType from(VehicleTypeEntity t);

	VehicleTypeEntity from(VehicleType type);

	VehicleTypeEntity getVehicleTypeById(long l) throws InvalidInformationException;
	
}

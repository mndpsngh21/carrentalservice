package com.mandeep.carrental.services;

import com.mandeep.carrental.entities.VehiceStatusEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.VehicleStatus;
import com.mandeep.carrental.responses.VehicleStatusResponse;

public interface VehicleStatusService {
	
	VehicleStatusResponse getVehicleStatus();
	
	void setDefaultStatus();

	VehicleStatus from(VehiceStatusEntity entity);

	VehiceStatusEntity from(VehicleStatus vehicleStatus);

	VehiceStatusEntity getStatusById(long id) throws InvalidInformationException;

	VehicleStatusResponse getAllStatuses();

}

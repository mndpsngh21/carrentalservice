package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.models.VehicleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleTypeResponse {

	String id;
	List<VehicleType> vehicleType;

	
	
	
}

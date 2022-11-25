package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.models.Vehicle;

import lombok.Data;

@Data
public class AvailbleCarResponse extends BaseResponse{
	
	public List<Vehicle> availableCar;

	
	
	

}

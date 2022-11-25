package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.models.Vehicle;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleListResponse extends BaseResponse{
	
	List<Vehicle> vehicles;

}

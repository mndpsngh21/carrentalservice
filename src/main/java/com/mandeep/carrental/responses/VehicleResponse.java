package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.models.Car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponse extends BaseResponse{
	
	List<Car> cars;
	
	

}

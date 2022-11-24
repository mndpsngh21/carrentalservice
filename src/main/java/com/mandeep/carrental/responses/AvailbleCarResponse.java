package com.mandeep.carrental.responses;

import java.util.ArrayList;

import com.mandeep.carrental.models.Car;

public class AvailbleCarResponse extends BaseResponse{
	
	public ArrayList<Car> availableCars;

	public ArrayList<Car> getAvailableCars() {
		return availableCars;
	}

	public void setAvailableCars(ArrayList<Car> availableCars) {
		this.availableCars = availableCars;
	}
	
	

}

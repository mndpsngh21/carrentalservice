package com.mandeep.carrental.services;

import com.mandeep.carrental.requests.ParkingRequest;
import com.mandeep.carrental.responses.AddParkingResponse;
import com.mandeep.carrental.responses.ParkingResponse;

public interface ParkingService {

	ParkingResponse getAllParkings();

	AddParkingResponse addParking(ParkingRequest request);

	AddParkingResponse deleteParking(String id);

	AddParkingResponse updateParking(ParkingRequest request);

}

package com.mandeep.carrental.services;

import com.mandeep.carrental.entities.ParkingEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.models.Location;
import com.mandeep.carrental.requests.ParkingRequest;
import com.mandeep.carrental.response.model.Parking;
import com.mandeep.carrental.responses.AddParkingResponse;
import com.mandeep.carrental.responses.ParkingResponse;

public interface ParkingService {

	ParkingResponse getAllParkings();

	AddParkingResponse addParking(ParkingRequest request);

	AddParkingResponse deleteParking(String id);

	AddParkingResponse updateParking(ParkingRequest request);

	void addDefaultParkings();

	Parking getParkingByLocation(double parkingLatitude, double parkingLongitude) throws InvalidInformationException;

	ParkingEntity getParkingById(String dropParkingId) throws InvalidInformationException;

}

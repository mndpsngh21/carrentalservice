package com.mandeep.carrental.responses;

import com.mandeep.carrental.response.model.Parking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddParkingResponse extends BaseResponse{
	
	Parking parking;

}

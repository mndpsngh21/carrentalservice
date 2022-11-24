package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.response.model.Parking;

import lombok.Getter;
import lombok.Setter;

/**
 * Response for get available parkings
 * @author mandeep
 *
 */
@Getter
@Setter
public class ParkingResponse extends BaseResponse{

	List<Parking> parkings;
	
	
}

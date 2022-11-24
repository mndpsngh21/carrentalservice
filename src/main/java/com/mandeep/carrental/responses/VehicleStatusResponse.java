package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.models.VehicleStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleStatusResponse extends BaseResponse{
	
	String id;
	List<VehicleStatus> status;

}

package com.mandeep.carrental.response.model;

import com.mandeep.carrental.models.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parking {
	String id;
	Location location;
	String address;
	String city;
	

}

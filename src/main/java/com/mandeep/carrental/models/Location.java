package com.mandeep.carrental.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
	double latitude;
	double longitude;

	public Location(double lat, double lng) {
		this.latitude=lat;
		this.longitude=lng;
	}

	public Location() {
		
	}
	
}

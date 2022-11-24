package com.mandeep.carrental.models;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum VehicleStatus {
	AVAILABLE,
	BOOKED,
	INUSE,
	NOT_AVAILABLE,
	UNDER_BOOKING
}

package com.mandeep.carrental.response.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Charges {
	
	public Charges() {
		
	}
	public Charges(double perKm,double hourlyCharge,double dailyCharges) {
		this.dailyCharges=dailyCharges;
		this.hourlyCharges=hourlyCharge;
		this.perKMCharges=perKm;
	}
	
	double perKMCharges;
	double hourlyCharges;
	double dailyCharges;
	

}

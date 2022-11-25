package com.mandeep.carrental.services;

import com.mandeep.carrental.entities.ChargesEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.response.model.Charges;

public interface ChargesService {

	public Charges getChargeService(Long chargeId) throws InvalidInformationException;
	void addDefault();
	public void setDefaultCharges();
	public ChargesEntity getChargesById(long l) throws InvalidInformationException;

}

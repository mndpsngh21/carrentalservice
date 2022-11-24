package com.mandeep.carrental.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.ChargesEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.repositories.ChargesRepository;
import com.mandeep.carrental.response.model.Charges;
import com.mandeep.carrental.responses.ChargeResponse;
import com.mandeep.carrental.services.ChargesService;

@Service
public class ChargesServiceImpl implements ChargesService{

	@Autowired
	ChargesRepository chargesRepository;
	
	
	@Override
	public Charges getChargeService(Long chargeId) throws InvalidInformationException {
		Optional<ChargesEntity> optional=   chargesRepository.findById(chargeId);
		if(!optional.isPresent()) {
			throw new InvalidInformationException("Invalid Charge Information");
		}
		return from(optional.get());
		
	}


	private Charges from(ChargesEntity chargesEntity) {
		Charges charges= new Charges();
		charges.setDailyCharges(chargesEntity.getDailyCharges());
		charges.setHourlyCharges(charges.getHourlyCharges());
		charges.setPerKMCharges(charges.getPerKMCharges());
		return charges;
	}


	@Override
	public void addDefault() {
		// TODO Auto-generated method stub
		
	}

}

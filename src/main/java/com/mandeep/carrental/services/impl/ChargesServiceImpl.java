package com.mandeep.carrental.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mandeep.carrental.entities.ChargesEntity;
import com.mandeep.carrental.exceptions.InvalidInformationException;
import com.mandeep.carrental.repositories.ChargesRepository;
import com.mandeep.carrental.response.model.Charges;
import com.mandeep.carrental.services.ChargesService;

@Service
public class ChargesServiceImpl implements ChargesService{
	Logger logger = LoggerFactory.getLogger(ChargesServiceImpl.class);

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
	
	private ChargesEntity from(Charges charges) {
		ChargesEntity chargesEntity= new ChargesEntity();
		chargesEntity.setDailyCharges(charges.getDailyCharges());
		chargesEntity.setHourlyCharges(charges.getHourlyCharges());
		chargesEntity.setPerKMCharges(charges.getPerKMCharges());
		return chargesEntity;
	}


	@Override
	public void addDefault() {
		
		
	}


	@Override
	public void setDefaultCharges() {
		chargesRepository.save(from(new Charges(5, 50, 500)));
		chargesRepository.save(from(new Charges(7, 100, 1000)));
	}


	@Override
	public ChargesEntity getChargesById(long id) throws InvalidInformationException {
		Optional<ChargesEntity> optional= chargesRepository.findById(id);
		if(!optional.isPresent()) {
			throw new InvalidInformationException("No Information found for vehicle status");
		}
		return optional.get();
	
	}

}

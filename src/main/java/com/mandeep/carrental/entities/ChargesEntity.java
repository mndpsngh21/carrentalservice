package com.mandeep.carrental.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChargesEntity extends MetaInfo{
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	Long id;
	
	@Column
	double perKMCharges;
	
	@Column
	double hourlyCharges;

	@Column
	double dailyCharges;


	@Column
	double fixedCharges;

	
}

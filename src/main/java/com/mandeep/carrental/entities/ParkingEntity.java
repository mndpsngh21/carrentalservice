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
public class ParkingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column
	private String uuid;
	
	@Column
	private String address;

	@Column
	private String city;
	
	@Column
	private Double latitude;
	
	@Column
	private Double longitude;
	

}

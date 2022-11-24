package com.mandeep.carrental.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VehicleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String uuid;
	@Column
	private String registerationNumber;
	@Column
	private String modelInformation;
	@Column
	private int numberOfSeats;
	@Column
	private String make;
	@Column
	private String model;
	@Column
	private double mileage;
	@Column
	private double parkingLatitude;
	@Column
	private double parkingLongitude;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "charges_id", referencedColumnName = "id")
	private ChargesEntity charges;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_id", referencedColumnName = "id")
	private VehicleTypeEntity vehicleType;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	private VehiceStatusEntity vehicleStatus;
	
	

}

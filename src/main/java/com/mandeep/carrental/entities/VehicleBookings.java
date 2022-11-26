package com.mandeep.carrental.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mandeep.carrental.models.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleBookings extends MetaInfo {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
Long id;

@Column
String bookingId;

@ManyToOne
@JoinColumn(name = "vehicleId")
VehicleEntity vehicleEntity;

@Column
String accountId;

@Column(columnDefinition = "TIMESTAMP")
LocalDateTime bookingFrom;

@Column(columnDefinition = "TIMESTAMP")
LocalDateTime bookingTill;


@Column(columnDefinition = "TIMESTAMP")
LocalDateTime returnOn;

@Column(columnDefinition = "TIMESTAMP")
LocalDateTime bookingDate;

@Column
String bookingStatus;

@Column
String pickupParkingId;

@Column
String dropParkingId;

@Column
Long startMeter;

@Column
Long endMeter;

}

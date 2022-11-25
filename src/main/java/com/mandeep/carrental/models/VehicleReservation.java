package com.mandeep.carrental.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleReservation {
    private String reservationId;
    private String usrId;
    private LocalDateTime reservationDate;
    private ReservationStatus status;
    private LocalDateTime fromDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private Location pickupLocation;
    private Location dropLocation;
    private double startMileage;
    private double endMileage;
    private String accocatedVehicleId;
    private VehicleType vehicleType;
    private String invoiceId;
}

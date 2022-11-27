package com.mandeep.carrental.services.impl;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.entities.ChargesEntity;
import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.models.Invoice;
import com.mandeep.carrental.models.User;
import com.mandeep.carrental.repositories.UserRepository;
import com.mandeep.carrental.responses.InvoiceResponse;
import com.mandeep.carrental.services.BookingService;
import com.mandeep.carrental.services.InvoiceService;
import com.mandeep.carrental.services.VehicleService;
import com.mandeep.carrental.utils.Constants;

@Service
public class DayInvoiceService implements InvoiceService {
	Logger logger = LoggerFactory.getLogger(DayInvoiceService.class);

    public static final double TAX_PERCENTAGE = .18;

    @Autowired
    VehicleService vehicleService;
    
    @Autowired
    BookingService bookingService;
    
    @Override
	public InvoiceResponse getEstimatedInvoice(String bookingId, boolean isFinal) {
    	InvoiceResponse response= new InvoiceResponse();
    	
    	Invoice invoice= computeInvoice(bookingId, isFinal);
    	response.setInvoice(invoice);
    	response.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
    	
		return response;
	}
    
	@Override
    public Invoice computeInvoice(String bookingId, boolean isFinal) {
		VehicleBookings booking=bookingService.getBookingForId(bookingId);		
        return buildInvoice(booking,isFinal);
    }

	/**
	 * Calculate invoice based on timings
	 * @param booking
	 * @param isFinal
	 * @return
	 */
    private Invoice buildInvoice(VehicleBookings booking, boolean isFinal) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID().toString());
        invoice.setReservationId(booking.getBookingId());
        User user = UserRepository.userUserIdMap.get(booking.getAccountId());
        invoice.setUserId(user.getId());
        Duration rentedDuration =null;
        if(isFinal) {
            rentedDuration = Duration.between(booking.getBookingFrom(),booking.getReturnOn());        	
        }
        else {
            rentedDuration = Duration.between(booking.getBookingFrom(),booking.getBookingTill());
        }
        double hours = Math.ceil(rentedDuration.toHours());

        double days       = Math.ceil(hours / 24);
        double extrahours = hours % 24;

        // read vehicle cost information
        VehicleEntity vehicleEntity=  booking.getVehicleEntity();
        ChargesEntity chargesEntity=  vehicleEntity.getCharges();
        
        double dailyCost = chargesEntity.getDailyCharges();
        double fixedCost = chargesEntity.getFixedCharges();

        double hourCost = extrahours* chargesEntity.getHourlyCharges();
        double rentalCost = days * dailyCost + fixedCost + hourCost;
        double taxes = rentalCost * TAX_PERCENTAGE;

        invoice.setUsageCharges(rentalCost);
        invoice.setTaxes(taxes);
        invoice.setTotal(rentalCost + taxes);
        return invoice;
    }

	
}

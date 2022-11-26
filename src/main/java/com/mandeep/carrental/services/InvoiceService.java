package com.mandeep.carrental.services;

import com.mandeep.carrental.entities.VehicleBookings;
import com.mandeep.carrental.models.Invoice;
import com.mandeep.carrental.responses.InvoiceResponse;

public interface InvoiceService {

	Invoice computeInvoice(String bookingId, boolean isFinal);

	InvoiceResponse getEstimatedInvoice(String bookingId, boolean b);
}

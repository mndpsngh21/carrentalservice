package com.mandeep.carrental.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.responses.InvoiceResponse;
import com.mandeep.carrental.services.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	
	InvoiceService invoiceService;
	
	@GetMapping("/estimated")
	public InvoiceResponse getEstimatedInvoice(@RequestParam("bookingId") String bookingId) {
		return invoiceService.getEstimatedInvoice(bookingId, false);
	}
	
	
	@GetMapping("/final")
	public InvoiceResponse getFinalInvoice(@RequestParam("bookingId") String bookingId ){
		return invoiceService.getEstimatedInvoice(bookingId, true);
	}
	

}

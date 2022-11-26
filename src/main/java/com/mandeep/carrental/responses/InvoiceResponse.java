package com.mandeep.carrental.responses;

import com.mandeep.carrental.models.Invoice;

import lombok.Data;

@Data
public class InvoiceResponse extends BaseResponse{

	Invoice invoice;
	
	   
}

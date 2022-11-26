package com.mandeep.carrental.response;

import com.mandeep.carrental.responses.BaseResponse;

import lombok.Data;

@Data
public class BookingCompletionResponse extends BaseResponse{
	String finalInvoiceUrl;
	

}

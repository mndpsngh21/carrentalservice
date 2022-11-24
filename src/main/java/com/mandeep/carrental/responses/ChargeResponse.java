package com.mandeep.carrental.responses;

import com.mandeep.carrental.response.model.Charges;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ChargeResponse extends BaseResponse{
	
	Charges charges;

}

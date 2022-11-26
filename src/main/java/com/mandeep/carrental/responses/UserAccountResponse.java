package com.mandeep.carrental.responses;

import com.mandeep.carrental.models.User;

import lombok.Data;

@Data
public class UserAccountResponse extends BaseResponse{

	User user;
	String token;
	
	
}

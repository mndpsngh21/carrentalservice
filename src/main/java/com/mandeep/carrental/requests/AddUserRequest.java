package com.mandeep.carrental.requests;

import com.mandeep.carrental.models.ContactInformation;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddUserRequest {
	 public String email;
	 public String userName;
	 public String password;
	 public ContactInformation contact;
}

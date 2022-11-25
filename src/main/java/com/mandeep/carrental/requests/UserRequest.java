package com.mandeep.carrental.requests;

import java.time.LocalDateTime;

import com.mandeep.carrental.models.ContactInformation;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRequest {
	 public String id;
	 public String email;
	 public String userName;
	 public String password;
	 public LocalDateTime lastLogin;
	 public ContactInformation contact;
}

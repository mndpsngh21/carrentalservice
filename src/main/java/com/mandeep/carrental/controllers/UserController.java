package com.mandeep.carrental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mandeep.carrental.requests.UserRequest;
import com.mandeep.carrental.responses.UserAccountListResponse;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;
	
	@PostMapping("/add")
	public UserAccountResponse addUser(@RequestBody UserRequest user) {
		 return service.addUser(user);
	}
	
	
	@GetMapping("/all")
	public UserAccountListResponse getUsers() {
		 return service.getUsers();
	}
	
	
	
	
}

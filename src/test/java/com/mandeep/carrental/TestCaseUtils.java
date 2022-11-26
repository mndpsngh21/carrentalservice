package com.mandeep.carrental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mandeep.carrental.requests.UserRequest;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.services.UserService;

@Component
public class TestCaseUtils {


	@Autowired
	UserService service;
	
public UserRequest getTestUser() {
	UserRequest userRequest= new UserRequest();
	userRequest.setUserName("TEST_USER1");
	userRequest.setPassword("TEST_USER1");
	userRequest.setEmail("dummy_1@email.com");
	return userRequest;
}
	


public UserAccountResponse addTestUser() {
	service.clearData();
	return service.addUser(getTestUser());
}
	
	
}

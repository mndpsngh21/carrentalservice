package com.mandeep.carrental;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mandeep.carrental.requests.UserRequest;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.services.UserService;
import com.mandeep.carrental.utils.Constants;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
	
	@Autowired
	UserService service;

	@Test
	public void createUserAndItShouldbePresent() {
		UserRequest userRequest= new UserRequest();
		userRequest.setUserName("Mandeep");
		userRequest.setPassword("Mandeep");
		userRequest.setEmail("dummy@email.com");
		service.addUser(userRequest);
		UserAccountResponse accountResponse= service.attemptLogin("Mandeep", "Mandeep");
		Assert.assertEquals("Mandeep", accountResponse.getUser().getUserName());
	}
	
	
	@Test
	public void duplicateUserDoesNotExists() {
		UserRequest userRequest= new UserRequest();
		userRequest.setUserName("Mandeep");
		userRequest.setPassword("Mandeep");
		userRequest.setEmail("dummy@email.com");
		UserAccountResponse response= service.addUser(userRequest);
		Assert.assertEquals(200, response.getCode());
		UserAccountResponse duplicate= service.addUser(userRequest);
		Assert.assertEquals(Constants.ResponseCode.DUPLICATE_RECORD, duplicate.getCode());	
	}
	
	@Test
	public void invalidUserDoesNotAllowedToLogin() {
		UserAccountResponse accountResponse= service.attemptLogin("SomeRandSome", "Mandeep");
		Assert.assertEquals(Constants.ResponseCode.NOT_FOUND, accountResponse.getCode());
	}
	
}

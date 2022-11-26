package com.mandeep.carrental.services;

import com.mandeep.carrental.requests.UserRequest;
import com.mandeep.carrental.responses.UserAccountListResponse;
import com.mandeep.carrental.responses.UserAccountResponse;

public interface UserService {
	
	UserAccountResponse addUser(UserRequest request);

	UserAccountListResponse getUsers();

	UserAccountResponse attemptLogin(String username, String password);

	void clearData();

}

package com.mandeep.carrental.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.models.Account;
import com.mandeep.carrental.models.User;
import com.mandeep.carrental.repositories.AccountRepository;
import com.mandeep.carrental.repositories.UserRepository;
import com.mandeep.carrental.requests.UserRequest;
import com.mandeep.carrental.responses.UserAccountListResponse;
import com.mandeep.carrental.responses.UserAccountResponse;
import com.mandeep.carrental.services.UserService;
import com.mandeep.carrental.utils.Constants;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public UserAccountResponse addUser(UserRequest request) {
		UserAccountResponse response= new UserAccountResponse();
		
		Account account = new User();
		account.setContact(request.getContact());
		account.setEmail(request.getEmail());
		account.setPassword(request.getPassword());
		account.setUserName(request.getUserName());
		Account saved =repository.createAccount(account);
	
		if(saved.getId()!=null &&! saved.getId().isEmpty()) {
			response.createDefaultSucces("Account created successfully");
		}
		else {
			response.createDefaultError("Failed to create user account");
		}
		return  response;
	}

	@Override
	public UserAccountListResponse getUsers() {
		UserAccountListResponse response= new UserAccountListResponse();
		List<User> users= repository.getAllUsers();
		response.setUsers(users);
		response.createDefaultSucces(Constants.ResponseMessages.DEFAULT);
		return response;
	}

}
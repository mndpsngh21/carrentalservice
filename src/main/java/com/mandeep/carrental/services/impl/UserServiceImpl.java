package com.mandeep.carrental.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mandeep.carrental.exceptions.UserAlreadyExistsException;
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
		Account saved;
		try {
			saved = repository.createAccount(account);
		} catch (UserAlreadyExistsException e) {
			//e.printStackTrace();
			response.createDefaultError("User is already present with this details",Constants.ResponseCode.DUPLICATE_RECORD);
			return  response;
		}
	
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

	@Override
	public UserAccountResponse attemptLogin(String username, String password) {
		UserAccountResponse response= new UserAccountResponse();
		
		Optional<User> user=   repository.findUser(username,password);
		if(user.isPresent()) {
			response.setToken(username);
			response.createDefaultSucces("User found!");
			response.setUser(user.get());
			return response;
		}
		response.createDefaultError("User Not found!", 404);
		return response;
	}

}

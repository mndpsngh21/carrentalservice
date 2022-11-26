package com.mandeep.carrental.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mandeep.carrental.entities.VehicleEntity;
import com.mandeep.carrental.exceptions.AccountDoesNotExistsException;
import com.mandeep.carrental.exceptions.UserAlreadyExistsException;
import com.mandeep.carrental.models.Account;
import com.mandeep.carrental.models.User;
import com.mandeep.carrental.models.VehicleReservation;

@Repository
public class UserRepository implements AccountRepository {
    public static Map<String, User> userMap = new HashMap<>();
    public static Map<String, User> userUserIdMap = new HashMap<>();
    public static List<User> users = new ArrayList<>();
    
    
    @Autowired
    VehicleRepository repository;
    

    public Account createAccount(Account account) throws UserAlreadyExistsException {
    	if(userUserIdMap.get(account.getUserName())!=null||userMap.get(account.getEmail())!=null) {
    		throw new UserAlreadyExistsException("User is already present");
    	}
    	
    	account.setId(UUID.randomUUID().toString());
        userMap.putIfAbsent(account.getEmail(), (User) account);
        userUserIdMap.putIfAbsent(account.getId(), (User) account);
        return account;
    }

    
    public void resetPassword(String email, String password) throws AccountDoesNotExistsException {
        if (userMap.get(email) == null)
            throw new AccountDoesNotExistsException("Account does not exist.");
        userMap.get(email).setPassword(password);
    }
    
    
    public List<User> getAllUsers(){
    	 return userMap.values().stream().map(u->hideCriticalInformation(u)).collect(Collectors.toList());
    }
    
    public User hideCriticalInformation(User u) {
    	u.setEmail(hideAlternateCharacter(u.getEmail()));
    	u.setPassword("<CRITICAL_INFORMATION>");
    	
    	return u;
    }

    private String hideAlternateCharacter(String email) {
    	if(email==null|| email.isEmpty()) {
    		return email;
    	}
    	
		StringBuffer buffer= new StringBuffer();
		int counter=0;
		for(char c:email.toCharArray()) {
			buffer.append(counter%2==0?'*':c);
			counter++;
		}
    	
		return buffer.toString();
	}

	public Optional<User> findUser(String username, String password) {
		User user  =  userUserIdMap.get(username);
		if(user==null||!user.getPassword().equals(password)) {
			return Optional.empty();
		}
		return Optional.of(user);
	}


	public void clearData() {
		// TODO Auto-generated method stub
		userMap.clear();
		users.clear();
		userUserIdMap.clear();
	}

    
    
    

}

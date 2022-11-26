package com.mandeep.carrental.repositories;

import com.mandeep.carrental.exceptions.AccountDoesNotExistsException;
import com.mandeep.carrental.exceptions.UserAlreadyExistsException;
import com.mandeep.carrental.models.Account;

public interface AccountRepository {
    Account createAccount(Account account) throws UserAlreadyExistsException;
    void resetPassword(String userId, String password) throws AccountDoesNotExistsException;
}


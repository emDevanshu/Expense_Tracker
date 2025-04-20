package com.training.expenseTracker.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(Integer id, String username) {
        super("User already exists with id " + id +" and name = " + username);
    }
}

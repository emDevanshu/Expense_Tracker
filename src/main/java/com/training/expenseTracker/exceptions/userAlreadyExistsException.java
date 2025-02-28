package com.training.expenseTracker.exceptions;

public class userAlreadyExistsException extends RuntimeException {
    public userAlreadyExistsException(Integer id, String username) {
        super("User already exists with id " + id +" and name = " + username);
    }
}

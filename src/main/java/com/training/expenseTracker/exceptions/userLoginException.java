package com.training.expenseTracker.exceptions;

public class userLoginException extends RuntimeException {
    public userLoginException() {
        super("Invalid User/ User does not exist");
    }
}

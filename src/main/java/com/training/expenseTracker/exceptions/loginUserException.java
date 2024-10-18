package com.training.expenseTracker.exceptions;

public class loginUserException extends RuntimeException {
    public loginUserException() {
        super("Invalid User/ User does not exist");
    }
}

package com.training.expenseTracker.exceptions;

public class UserLoginException extends RuntimeException {
    public UserLoginException() {
        super("Invalid Username/password, Plz re-enter the correct credentials");
    }
}

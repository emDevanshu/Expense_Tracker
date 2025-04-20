package com.training.expenseTracker.exceptions;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException() {
        super("User does not exist, Please register first");
    }
}

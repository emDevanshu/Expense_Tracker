package com.training.expenseTracker.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Integer id) {
        super("Expense Not Found with id " + id);
    }
}

package com.training.expenseTracker.service;

import com.training.expenseTracker.exceptions.ExpenseNotFoundException;
import com.training.expenseTracker.model.Expense;
import com.training.expenseTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired ExpenseRepository expenseRepository;

    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    public void addExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public void deleteExpenseById(Integer id) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if(expense.isPresent()) expenseRepository.delete(expense.get());
        else throw new ExpenseNotFoundException(id);
    }
}

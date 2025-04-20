package com.training.expenseTracker.controller;

import com.training.expenseTracker.model.Expense;
import com.training.expenseTracker.model.User;
import com.training.expenseTracker.service.ExpenseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseController {

    @Autowired ExpenseService expenseService;

    @PostConstruct
    public void init() {
        Expense expense1 = new Expense();
        expense1.setId(1);
        expense1.setTitle("lassi");
        expense1.setAmount(100.0);
        expense1.setCategory("Food");

        Expense expense2 = new Expense();
        expense2.setId(2);
        expense2.setTitle("shirt");
        expense2.setAmount(2000.0);
        expense2.setCategory("Clothing");

        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
    }

    @GetMapping("/show")
    public List<Expense> getAllExpense() {
        return expenseService.getAllExpense();
    }

    @PostMapping("/add")
    public void addExpense(@RequestBody Expense expense) {
        expenseService.addExpense(expense);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteExpenseById(@RequestParam Integer id) {
        expenseService.deleteExpenseById(id);
        return new ResponseEntity<>("Expense deleted successfully", HttpStatus.OK);
    }
}

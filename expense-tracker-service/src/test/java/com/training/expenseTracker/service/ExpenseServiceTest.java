package com.training.expenseTracker.service;

import com.training.expenseTracker.exceptions.ExpenseNotFoundException;
import com.training.expenseTracker.model.Expense;
import com.training.expenseTracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // This annotation integrates Mockito with JUnit 5
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;  // Mock the repository

    @InjectMocks
    private ExpenseService expenseService;  // Inject mocks into the service

    private Expense expense;

    @BeforeEach
    public void setUp() {
        expense = new Expense();
        expense.setId(1);
        expense.setTitle("Test Expense");
        expense.setAmount(100.0);
    }

    @Test
    public void testGetAllExpense() {
        // Mock repository behavior
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(expense));

        // Call the service method
        List<Expense> expenses = expenseService.getAllExpense();

        // Assertions
        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals("Test Expense", expenses.get(0).getTitle());

        // Verify repository interaction
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllExpense_EmptyList() {
        when(expenseRepository.findAll()).thenReturn(Collections.emptyList());

        List<Expense> expenses = expenseService.getAllExpense();

        assertNotNull(expenses);
        assertTrue(expenses.isEmpty(), "The expense list should be empty");

        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    public void testAddExpense() {
        // Mock repository behavior
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // Call the service method
        expenseService.addExpense(expense);

        // Verify repository interaction
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    public void testAddExpense_NullExpense() {
        assertThrows(IllegalArgumentException.class, () -> {
            expenseService.addExpense(null);
        });
    }

    @Test
    public void testDeleteExpenseById() {
        // Mock repository behavior
        when(expenseRepository.findById(1)).thenReturn(Optional.of(expense));

        // Call the service method
        expenseService.deleteExpenseById(1);

        // Verify repository interaction
        verify(expenseRepository, times(1)).delete(expense);
    }

    @Test
    public void testDeleteExpenseById_NotFound() {
        // Mock repository behavior
        when(expenseRepository.findById(1)).thenReturn(Optional.empty());

        // Call the service method and expect exception
        ExpenseNotFoundException exception = assertThrows(ExpenseNotFoundException.class, () -> {
            expenseService.deleteExpenseById(1);
        });

        // Assertions
        assertEquals("Expense Not Found with id 1", exception.getMessage());
    }

    @Test
    public void testDeleteExpenseById_DeleteFails() {
        when(expenseRepository.findById(1)).thenReturn(Optional.of(expense));

        // Simulate a failure in the repository when trying to delete
        doThrow(new RuntimeException("Delete failed")).when(expenseRepository).delete(expense);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            expenseService.deleteExpenseById(1);
        });

        assertEquals("Delete failed", exception.getMessage());
    }

    // Test case where expense is added and returned with a null ID
    @Test
    public void testAddExpense_WithNullId() {
        expense.setId(null);

        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        expenseService.addExpense(expense);

        verify(expenseRepository, times(1)).save(expense);
    }

    // Test if ExpenseService interacts correctly with the ExpenseRepository multiple times
    @Test
    public void testMultipleInteractions() {
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(expense));
        when(expenseRepository.findById(1)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // Test getAllExpense()
        expenseService.getAllExpense();

        // Test addExpense()
        expenseService.addExpense(expense);

        // Test deleteExpenseById()
        expenseService.deleteExpenseById(1);

        verify(expenseRepository, times(1)).findAll();
        verify(expenseRepository, times(1)).findById(1);
        verify(expenseRepository, times(1)).save(expense);
        verify(expenseRepository, times(1)).delete(expense);
    }
}

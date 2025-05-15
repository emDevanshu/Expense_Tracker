package com.training.expenseTracker.controller;

import com.training.expenseTracker.model.Expense;
import com.training.expenseTracker.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExpenseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
        expense = new Expense(1, "lassi", 100.0, "Food");
    }

    @Test
    void testGetAllExpense() throws Exception {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.getAllExpense()).thenReturn(expenses);

        mockMvc.perform(get("/api/v1/expense/show"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("lassi"))
                .andExpect(jsonPath("$[0].amount").value(100.0));
    }

    @Test
    void testAddExpense() throws Exception {
        Expense newExpense = new Expense(2, "shirt", 2000.0, "Clothing");

        mockMvc.perform(post("/api/v1/expense/add")
                        .contentType("application/json")
                        .content("{\"id\":2, \"title\":\"shirt\", \"amount\":2000.0, \"category\":\"Clothing\"}"))
                .andExpect(status().isCreated());

        verify(expenseService, times(1)).addExpense(newExpense);
    }

    @Test
    void testDeleteExpenseById() throws Exception {
        doNothing().when(expenseService).deleteExpenseById(expense.getId());

        mockMvc.perform(delete("/api/v1/expense/delete")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense deleted successfully"));

        verify(expenseService, times(1)).deleteExpenseById(expense.getId());
    }
}

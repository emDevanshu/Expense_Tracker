package com.training.expenseTracker.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseNotFoundExceptionTest {

    @Test
    public void testExpenseNotFoundExceptionMessage() {
        // Given an ID for which the exception will be thrown
        Integer id = 123;

        // When the exception is thrown
        ExpenseNotFoundException exception = assertThrows(ExpenseNotFoundException.class, () -> {
            throw new ExpenseNotFoundException(id);
        });

        // Then verify the message in the exception
        assertEquals("Expense Not Found with id " + id, exception.getMessage());
    }
}

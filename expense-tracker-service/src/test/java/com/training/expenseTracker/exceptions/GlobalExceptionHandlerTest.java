package com.training.expenseTracker.exceptions;

import com.training.expenseTracker.dto.UserErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleUserLoginException() {
        UserLoginException ex = new UserLoginException();
        ResponseEntity<UserErrorDTO> response = handler.handleUserLoginException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid Username/password, Plz re-enter the correct credentials", response.getBody().getMessage());
    }

    @Test
    void testHandleUserAlreadyExists() {
        Integer id = 42;
        String username = "alice";
        UserAlreadyExistsException ex = new UserAlreadyExistsException(id, username);
        ResponseEntity<UserErrorDTO> response = handler.handleUserAlreadyExists(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User already exists with id 42 and name = alice", response.getBody().getMessage());
    }


    @Test
    void testHandleExpenseNotFoundException() {
        Integer id = 99;
        ExpenseNotFoundException ex = new ExpenseNotFoundException(id);
        ResponseEntity<?> response = handler.handleExpenseNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Expense Not Found with id 99", response.getBody());
    }
}

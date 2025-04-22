package com.training.expenseTracker.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotExistsExceptionTest {

    @Test
    void testDefaultExceptionMessage() {
        UserNotExistsException exception = new UserNotExistsException();
        assertEquals("User does not exist, Please register first", exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }
}

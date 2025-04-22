package com.training.expenseTracker.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginExceptionTest {

    @Test
    void testDefaultExceptionMessage() {
        UserLoginException exception = new UserLoginException();
        assertEquals(
                "Invalid Username/password, Plz re-enter the correct credentials",
                exception.getMessage()
        );
        assertInstanceOf(RuntimeException.class, exception);
    }
}

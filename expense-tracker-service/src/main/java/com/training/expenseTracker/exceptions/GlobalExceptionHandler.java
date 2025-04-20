package com.training.expenseTracker.exceptions;

import com.training.expenseTracker.dto.UserErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<UserErrorDTO> handleUserLoginException(UserLoginException e) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(e.getMessage());
        return new ResponseEntity<>(userErrorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserErrorDTO> handleUserAlreadyExists(UserAlreadyExistsException e) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(e.getMessage());
        return new ResponseEntity<>(userErrorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<?> handleExpenseNotFoundException(ExpenseNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

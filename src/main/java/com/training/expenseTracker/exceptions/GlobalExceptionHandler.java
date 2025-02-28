package com.training.expenseTracker.exceptions;

import com.training.expenseTracker.dto.UserErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(userLoginException.class)
    public ResponseEntity<UserErrorDTO> handleUserLoginException(userLoginException e) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(e.getMessage());
        return new ResponseEntity<>(userErrorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(userAlreadyExistsException.class)
    public ResponseEntity<UserErrorDTO> handleUserAlreadyExists(userAlreadyExistsException e) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(e.getMessage());
        return new ResponseEntity<>(userErrorDTO, HttpStatus.CONFLICT);
    }
}

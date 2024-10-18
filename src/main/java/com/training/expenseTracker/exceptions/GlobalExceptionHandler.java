package com.training.expenseTracker.exceptions;

import com.training.expenseTracker.dto.UserErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(loginUserException.class)
    public ResponseEntity<UserErrorDTO> loginUserException(loginUserException e) {
        UserErrorDTO userErrorDTO = new UserErrorDTO(e.getMessage());
        return new ResponseEntity<>(userErrorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(userAlreadyExists.class)
    public ResponseEntity<UserErrorDTO> userAlreadyExists(userAlreadyExists e) {
        UserErrorDTO userErrorDTO = new UserErrorDTO("User already exists");
        return new ResponseEntity<>(userErrorDTO, HttpStatus.CONFLICT);
    }
}

package com.training.expenseTracker.service;

import com.training.expenseTracker.exceptions.UserAlreadyExistsException;
import com.training.expenseTracker.exceptions.UserLoginException;
import com.training.expenseTracker.model.User;
import com.training.expenseTracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        assertEquals(1, userService.getAllUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testRegisterUser_Success() throws UserAlreadyExistsException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        userService.registerUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_AlreadyExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginUser_Success() throws UserLoginException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.loginUser(user.getEmail(), user.getPassword()));
    }

    @Test
    void testLoginUser_WrongPassword() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserLoginException.class, () -> userService.loginUser(user.getEmail(), "wrongPassword"));
    }
}
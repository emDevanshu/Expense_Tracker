package com.training.expenseTracker.service;

import com.training.expenseTracker.exceptions.UserAlreadyExistsException;
import com.training.expenseTracker.exceptions.UserLoginException;
import com.training.expenseTracker.exceptions.UserNotExistsException;
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
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

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

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getUserById(user.getId());

        assertEquals(user.getId(), result.getId());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(user.getId()));

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testAddUserInput() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUserInput(user);

        assertEquals(user.getName(), result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserEmail() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUserEmail(user.getId(), "newEmail@example.com");

        assertEquals("newEmail@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }
}

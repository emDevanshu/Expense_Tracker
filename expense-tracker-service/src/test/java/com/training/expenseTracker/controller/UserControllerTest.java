package com.training.expenseTracker.controller;

import com.training.expenseTracker.exceptions.UserLoginException;
import com.training.expenseTracker.model.User;
import com.training.expenseTracker.repository.UserRepository;
import com.training.expenseTracker.service.UserService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User(1, "Iron man", "ironman@example.com", "password123");
    }

    @Test
    void testDisplay() throws Exception {
        mockMvc.perform(get("/api/v1/user/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!! You successfully created your first API"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user/show"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Iron man"))
                .andExpect(jsonPath("$[0].email").value("ironman@example.com"));
    }

    @Test
    void testRegisterUser() throws Exception {
        // Create a User object with a null id (id will be auto-generated)
        User newUser = new User(null, "Superman", "superman@example.com", "password123");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType("application/json")
                        .content("{\"name\":\"Superman\", \"email\":\"superman@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User has been Registered Successfully"));

        // Verify that the registerUser method was called with a User object with null id
        verify(userService, times(1)).registerUser(argThat(user -> user.getId() == null &&
                "Superman".equals(user.getName()) &&
                "superman@example.com".equals(user.getEmail()) &&
                "password123".equals(user.getPassword())));
    }


    @Test
    void testLoginUser_Success() throws UserLoginException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Mock successful login (no exception expected)
        doNothing().when(userService).loginUser(user.getEmail(), user.getPassword());

        assertDoesNotThrow(() -> userService.loginUser(user.getEmail(), user.getPassword()));

        verify(userService, times(1)).loginUser(user.getEmail(), user.getPassword());
    }

    @Test
    void testLoginUser_Failure() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Mock failed login (throws exception)
        doThrow(new UserLoginException()).when(userService).loginUser(user.getEmail(), "wrongPassword");

        assertThrows(UserLoginException.class, () -> userService.loginUser(user.getEmail(), "wrongPassword"));

        verify(userService, times(1)).loginUser(user.getEmail(), "wrongPassword");
    }
}

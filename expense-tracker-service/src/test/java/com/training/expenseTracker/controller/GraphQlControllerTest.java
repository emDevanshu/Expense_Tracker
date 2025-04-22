package com.training.expenseTracker.controller;

import com.training.expenseTracker.model.User;
import com.training.expenseTracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GraphQlControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GraphQlController graphQlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllUsers() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@example.com", "pass"),
                new User(2, "Bob", "bob@example.com", "pass2")
        );
        when(userService.getAllUsers()).thenReturn(mockUsers);

        List<User> result = graphQlController.allUsers();
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void testGetUserById() {
        User user = new User(1, "Alice", "alice@example.com", "pass");
        when(userService.getUserById(1)).thenReturn(user);

        User result = graphQlController.getUserById(1);
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    void testAddUser() {
        User user = new User(1, "Charlie", "charlie@example.com", "pass3");
        when(userService.addUser("Charlie", "charlie@example.com", "pass3")).thenReturn(user);

        User result = graphQlController.addUser("Charlie", "charlie@example.com", "pass3");
        assertNotNull(result);
        assertEquals("Charlie", result.getName());
    }

    @Test
    void testAddUserInput() {
        GraphQlController.UserInput userInput = new GraphQlController.UserInput("Dana", "dana@example.com", "pass4");
        User user = new User(null, "Dana", "dana@example.com", "pass4");
        when(userService.addUserInput(any(User.class))).thenReturn(user);

        User result = graphQlController.addUserInput(userInput);
        assertNotNull(result);
        assertEquals("Dana", result.getName());
    }

    @Test
    void testUpdateUserEmail() {
        User updatedUser = new User(1, "Alice", "alice@new.com", "pass");
        when(userService.updateUserEmail(1, "alice@new.com")).thenReturn(updatedUser);

        User result = graphQlController.updateUserEmail(1, "alice@new.com");
        assertNotNull(result);
        assertEquals("alice@new.com", result.getEmail());
    }
}

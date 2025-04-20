package com.training.expenseTracker.controller;

import com.training.expenseTracker.model.User;
import com.training.expenseTracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
//@Tag(name = "Kitu", description = "Khana bahut acha tha")
public class UserController {

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setName("Iron man");
        user1.setEmail("ironman@example.com");
        user1.setPassword("password123");

        User user2 = new User();
        user2.setName("superman");
        user2.setEmail("superman@example.com");
        user2.setPassword("password123");

        userService.registerUser(user1);
        userService.registerUser(user2);
    }

    @GetMapping("/hello")
    public String display(){
        return "Hello!! You successfully created your first API";
    }

    @Operation(summary = "Get all users", description = "Fetch all users in the system")
    @GetMapping("/show")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>("User has been Registered Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        userService.loginUser(email, password);
        return new ResponseEntity<>("User successfull login", HttpStatus.CREATED);
    }
}
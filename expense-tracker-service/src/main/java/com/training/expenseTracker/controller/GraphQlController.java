package com.training.expenseTracker.controller;

import com.training.expenseTracker.model.User;
import com.training.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/user")
public class GraphQlController {
    @Autowired
    UserService userService;

    @QueryMapping
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public User getUserById(@Argument Integer id) {
        return userService.getUserById(id);
    }

    @MutationMapping
    public User addUser(@Argument String name, @Argument String email, @Argument String password) {
        return userService.addUser(name, email, password);
    }

    @MutationMapping
    public User addUserInput(@Argument UserInput userInput) {
        User user = new User(null, userInput.name(), userInput.email(), userInput.password());
        System.out.println(userInput.name());
        return userService.addUserInput(user);
    }

    @MutationMapping
    public User updateUserEmail(@Argument Integer id, @Argument String email) {
        return userService.updateUserEmail(id, email);
    }

    record UserInput(String name, String email, String password) {}
}

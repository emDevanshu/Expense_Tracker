//package com.training.expenseTracker.controller;
//
//import com.training.expenseTracker.model.User;
//import com.training.expenseTracker.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/user")
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    @GetMapping("/welcome/{name}")
//    public String welcome(@PathVariable String name) {
//        return "Welcome to the project, " + name + "!!!";
//    }
//
//    @GetMapping("/show")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        userService.registerUser(user);
//        return new ResponseEntity<>("User has been Registered Successfully", HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
//        userService.loginUser(email, password);
//        return new ResponseEntity<>("User successfull login", HttpStatus.CREATED);
//    }
//}

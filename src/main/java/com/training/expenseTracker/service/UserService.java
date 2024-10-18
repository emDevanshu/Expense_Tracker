package com.training.expenseTracker.service;

import com.training.expenseTracker.exceptions.loginUserException;
import com.training.expenseTracker.exceptions.userAlreadyExists;
import com.training.expenseTracker.model.User;
import com.training.expenseTracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void registerUser(User user) throws userAlreadyExists{
        String email = user.getEmail();

        if(userRepository.findByEmail(email).isPresent()) {
            throw new userAlreadyExists();
        }

        userRepository.save(user);
    }

    public void loginUser(String email, String password) throws loginUserException {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            if(!user.get().getPassword().equals(password)) {
                throw new loginUserException();
            }
        }
    }
}

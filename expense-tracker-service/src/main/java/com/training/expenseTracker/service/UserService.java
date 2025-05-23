package com.training.expenseTracker.service;

import com.training.expenseTracker.exceptions.UserLoginException;
import com.training.expenseTracker.exceptions.UserAlreadyExistsException;
import com.training.expenseTracker.exceptions.UserNotExistsException;
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

    public void registerUser(User user) throws UserAlreadyExistsException {
        String email = user.getEmail();
        Optional<User> searchUser = userRepository.findByEmail(email);

        if (searchUser.isPresent()) throw new UserAlreadyExistsException(searchUser.get().getId(), searchUser.get().getName());
        else userRepository.save(user);
    }

    public void loginUser(String email, String password) throws UserLoginException, UserNotExistsException {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            if(!user.get().getPassword().equals(password)) {
                throw new UserLoginException();
            }
        }
        else {
            throw new UserNotExistsException();
        }
    }

    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public User addUser(String name,  String email,  String password) {
        User newUser = new User(null, name, email, password);
        return userRepository.save(newUser);
    }

    public User addUserInput(User user) {
        return userRepository.save(user);
    }

    public User updateUserEmail(Integer id, String email) {
        User user = getUserById(id);
        user.setEmail(email);
        return userRepository.save(user);
    }
}

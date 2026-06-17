package com.bindu.smartexpensesystem.service;

import java.util.List;
import java.util.Optional;

import com.bindu.smartexpensesystem.dto.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bindu.smartexpensesystem.entity.User;
import com.bindu.smartexpensesystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id).orElseThrow();

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        return userRepository.save(user);
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }
    
    public User login(LoginRequest request) {

        Optional<User> user =
                userRepository.findByEmail(request.getEmail());

        if (user.isPresent()
                && passwordEncoder.matches(
                        request.getPassword(),
                        user.get().getPassword()
                )) {

            return user.get();
        }
        throw new RuntimeException(
                "Invalid Email or Password"
        );
    }
}
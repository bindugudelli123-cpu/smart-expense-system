package com.bindu.smartexpensesystem.controller;
import com.bindu.smartexpensesystem.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.bindu.smartexpensesystem.dto.LoginRequest;
import com.bindu.smartexpensesystem.dto.LoginResponse;
import com.bindu.smartexpensesystem.security.JwtUtil;

import com.bindu.smartexpensesystem.entity.User;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	@Autowired
	private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "Smart Expense System Running";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
    	return userService.getAllUsers();
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
    	return userService.createUser(user);
    }
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody User updatedUser) {

        return userService.updateUser(id, updatedUser);
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        return userService.deleteUser(id);
    }
    
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        User user = userService.login(request);

        String token =
                JwtUtil.generateToken(
                        user.getEmail()
                );

        return new LoginResponse(token);
    }
}
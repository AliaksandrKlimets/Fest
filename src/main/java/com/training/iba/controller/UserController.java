package com.training.iba.controller;

import com.training.iba.entity.User;
import com.training.iba.repository.UserRepository;
import com.training.iba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id){
        return userRepository.findById(id);
    }

    @PostMapping
    public User registerUser(@RequestBody User user){
        System.out.println("AAAAA");
        return userRepository.save(user);
    }
}

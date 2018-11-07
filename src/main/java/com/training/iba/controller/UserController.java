package com.training.iba.controller;

import com.training.iba.entity.User;
import com.training.iba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Iterable<User> users() {
        return userRepository.findAll();
    }
}

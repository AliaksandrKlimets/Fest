package com.training.iba.controller;

import com.training.iba.entity.Festival;
import com.training.iba.entity.User;
import com.training.iba.repository.UserRepository;
import com.training.iba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;


    @GetMapping
    public List<User> users() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/add")
    public void registerUser(@RequestBody User user) {
        user.setAnon(false);
        userService.registerUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") User user) {
        userRepository.delete(user);
    }

    @PutMapping("/change/user")
    public User changeUser(@RequestBody User user) {
        return userRepository.save(user);
    }

//    @PostMapping("/login")
//    public User login(@RequestBody User user){
//        System.out.println(user.getUsername());
//        System.out.println(encoder.encode(user.getPassword()));
//       // User us = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//        //System.out.println(us.getId());
//        //System.out.println(us.getUsername());
//        return userRepository.findByUsername(user.getUsername());
//    }
}

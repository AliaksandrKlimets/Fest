package com.training.iba.controller;

import com.training.iba.entity.User;
import com.training.iba.repository.UserRepository;
import com.training.iba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User currentUser;

    @GetMapping
    public List<User> users() {

        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/add")
    public User registerUser(@RequestBody User user) throws NoSuchAlgorithmException {
        if (userRepository.findByUsername(user.getUsername()) != null) return null;
        user.setAnon(false);
        userService.registerUser(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") User user) {
        userRepository.delete(user);
    }

    @PutMapping("/change/{id}")
    public User changeUser(@RequestBody User user, @PathVariable("id") long id) {
        User us = userRepository.findByUsername(user.getUsername());
        if(us!=null && us.getId()!=id) return null;
        user.setId(id);
        User usr = userRepository.findById(id);
        user.setFestivals(usr.getFestivals());
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) throws NoSuchAlgorithmException {
        currentUser = userRepository.findByUsernameAndPassword(user.getUsername(),
                userService.encode(user.getPassword()));
        if (currentUser != null) {
            currentUser.setPassword("");
        }
        return currentUser;
    }

    @GetMapping("/authorized")
    public User checkAuth(){
        return currentUser;
    }

    @GetMapping("/logout")
    public User logout(){
        currentUser = null;
        return null;
    }

    @PostMapping("/{id}/mailing-access/open")
    public void openMailingAccess(@PathVariable("id") User user){
        user.setMailingAccess(true);
        userRepository.save(user);
    }

    @PostMapping("/{id}/mailing-access/close")
    public void closeMailingAccess(@PathVariable("id") User user){
        user.setMailingAccess(false);
        userRepository.save(user);
    }

    @PostMapping("/{id}/change-password")
    public Boolean changePassword(@PathVariable("id") User user, String oldPass, String newPass) throws NoSuchAlgorithmException{
        return userService.changePassword(user, oldPass, newPass);
    }
}

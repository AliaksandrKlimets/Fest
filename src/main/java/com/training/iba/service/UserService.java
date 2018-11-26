package com.training.iba.service;

import com.training.iba.entity.Role;
import com.training.iba.entity.User;
import com.training.iba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        // return user;
        return null;
    }

    public void registerUser(User user) throws NoSuchAlgorithmException {
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getUsers() {
        Iterable<User> users = userRepository.findAll();
        List<User> currentUsers = new ArrayList<>();
        for (User user : users) {
            if (!user.isAnon() && !user.getRoles().contains(Role.ADMIN)) currentUsers.add(user);
        }
        return currentUsers;
    }

    public String encode(String pass) throws NoSuchAlgorithmException {
        StringBuilder code = new StringBuilder();
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("MD5");
        byte bytes[] = pass.getBytes();
        byte digest[] = messageDigest.digest(bytes);
        for (byte aDigest : digest) {
            code.append(Integer.toHexString(0x0100 + (aDigest & 0x00FF)).substring(1));
        }

        pass = code.toString();
        return pass;
    }


    public Boolean changePassword(User user, String oldPass, String newPass) throws NoSuchAlgorithmException{
        if(!user.getPassword().equals(encode(oldPass))) return false;
        user.setPassword(encode(newPass));
        userRepository.save(user);
        return true;
    }
}

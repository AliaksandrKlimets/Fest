package com.training.iba.repository;

import com.training.iba.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findById(long id);
    User findByUsernameAndPassword(String username, String password);
    User findByActivationCode(String code);
}

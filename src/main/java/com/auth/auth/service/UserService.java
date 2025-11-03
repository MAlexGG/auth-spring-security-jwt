package com.auth.auth.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth.auth.entity.User;

public interface UserService {

    User getUserByUsername(String name);
    ResponseEntity<User> getUserById(Integer id);
    ResponseEntity<List<User>> getUsers();
    UserDetails loadUserByUsername(String username);
}

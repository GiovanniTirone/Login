package com.example.login2.users.controllers;

import com.example.login2.auth.services.LoginService;
import com.example.login2.security.WebSecurity;
import com.example.login2.users.entities.User;
import com.example.login2.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    public ResponseEntity<User> getProfile () {
        Optional<User> userOpt = WebSecurity.getCurrentUser();
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

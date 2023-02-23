package com.example.login2.orders.controllers;


import com.example.login2.users.entities.User;
import com.example.login2.users.utils.Roles;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
//@PreAuthorize("hasRole('ROLE_"+Roles.REGISTERED+"')")
public class OrderController {


    @GetMapping
    public User getAll () {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @GetMapping("/security")
    public Object getSecurityHolder () {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }




}

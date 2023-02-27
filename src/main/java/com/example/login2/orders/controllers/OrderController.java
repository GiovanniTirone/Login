package com.example.login2.orders.controllers;


import com.example.login2.orders.entities.Order;
import com.example.login2.orders.entities.OrderStatusEnum;
import com.example.login2.orders.repos.OrdersRepo;
import com.example.login2.orders.services.OrderService;


import com.example.login2.security.WebSecurity;
import com.example.login2.users.entities.User;
import com.example.login2.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('ROLE_"+ Roles.REGISTERED+"')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepo ordersRepo;

    @GetMapping("/currentUser")
    public Optional<User> get () {
        return WebSecurity.getCurrentUser();
    }

    @GetMapping("/authentication")
    public Authentication getAuth () {
        return  SecurityContextHolder.getContext().getAuthentication();
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        order.setStatus(OrderStatusEnum.CREATED);
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Optional<Order> orderOpt = ordersRepo.findById(id);
        if (orderOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderOpt.get());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() throws Exception {
        Optional<User> userOpt = WebSecurity.getCurrentUser();
        if(userOpt.isEmpty()) throw new Exception("Cannot find user");
        return ResponseEntity.ok(ordersRepo.findByCreatedBy(userOpt.get()));
    }


}












    /*
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
*/
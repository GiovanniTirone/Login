package com.example.login2.orders.controllers;


import com.example.login2.orders.entities.Order;
import com.example.login2.orders.repos.OrdersRepo;
import com.example.login2.orders.services.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/orders/{id}/state")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;

    @Autowired
    private OrdersRepo ordersRepo;

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/accepted")
    public ResponseEntity<Order> accepted (@PathVariable Long id) throws Exception {
        Optional<Order> orderOpt = ordersRepo.findById(id);
        if(orderOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setAccepted(orderOpt.get()));
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/in-preparation")
    public ResponseEntity<Order> inPreparation (@PathVariable Long id) throws Exception {
        Optional<Order> orderOpt = ordersRepo.findById(id);
        if(orderOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setInPreparation(orderOpt.get()));
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/ready")
    public ResponseEntity<Order> ready (@PathVariable Long id) throws Exception {
        Optional<Order> orderOpt = ordersRepo.findById(id);
        if(orderOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setReady(orderOpt.get()));
    }

    @PreAuthorize("hasRole('ROLE_RIDER')")
    @PutMapping("/delivering")
    public ResponseEntity<Order> delivering (@PathVariable Long id) throws Exception {
        Optional<Order> orderOpt = ordersRepo.findById(id);
        if(orderOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setDelivering(orderOpt.get()));
    }

    @PreAuthorize("hasRole('ROLE_RIDER')")
    @PutMapping("/complete")
    public ResponseEntity<Order> complete (@PathVariable Long id) throws Exception {
        Optional<Order> orderOpt = ordersRepo.findById(id);
        if(orderOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setComplete(orderOpt.get()));
    }


}

package com.example.login2.orders.services;

import com.example.login2.orders.entities.Order;
import com.example.login2.orders.repos.OrdersRepo;
import com.example.login2.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepo ordersRepo;

    public Order save (Order order){
        if(order == null) return null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        return ordersRepo.save(order);
    }

    public Order update (Long id, Order order){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setId(id);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user);
        return ordersRepo.save(order);
    }


    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> order = ordersRepo.findById(id);
        if(order.isEmpty()) return false;
        return order.get().getId().equals(user.getId());
    }
}

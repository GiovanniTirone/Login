package com.example.login2.orders.services;

import com.example.login2.orders.entities.Order;
import com.example.login2.orders.entities.OrderStatusEnum;
import com.example.login2.orders.repos.OrdersRepo;
import com.example.login2.security.WebSecurity;
import com.example.login2.users.services.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStateService {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private RiderService riderService;

    public Order setAccepted(Order order) throws Exception {
        if(order.getStatus() != OrderStatusEnum.CREATED)
            throw new Exception("Cannot edit order");
        if(WebSecurity.checkCurrentUser(order.getRestaurant().getId()))
            throw new Exception("This is not your order");
        order.setStatus(OrderStatusEnum.ACCEPTED);
        return ordersRepo.save(order);
    }

    public Order setInPreparation(Order order) throws Exception {
        if(order.getStatus() != OrderStatusEnum.ACCEPTED)
            throw new Exception("Cannot edit order");
        if(WebSecurity.checkCurrentUser(order.getRestaurant().getId()))
            throw new Exception("This is not your order");
        order.setStatus(OrderStatusEnum.IN_PREPARATION);
        return ordersRepo.save(order);
    }

    public Order setReady(Order order) throws Exception {
        if(order.getStatus() != OrderStatusEnum.IN_PREPARATION)
            throw new Exception("Cannot edit order");
        if(WebSecurity.checkCurrentUser(order.getRestaurant().getId()))
            throw new Exception("This is not your order");
        order.setRider(riderService.pickRider());
        order.setStatus(OrderStatusEnum.READY);
        return ordersRepo.save(order);
    }

    public Order setDelivering(Order order) throws Exception {
        if(order.getStatus() != OrderStatusEnum.READY)
            throw new Exception("Cannot edit order");
        if(WebSecurity.checkCurrentUser(order.getRider().getId()))
            throw new Exception("This is not your order");
        order.setStatus(OrderStatusEnum.DELIVERING);
        return ordersRepo.save(order);
    }

    public Order setComplete(Order order) throws Exception {
        if(order.getStatus() != OrderStatusEnum.DELIVERING)
            throw new Exception("Cannot edit order");
        if(WebSecurity.checkCurrentUser(order.getRider().getId()))
            throw new Exception("This is not your order");
        order.setStatus(OrderStatusEnum.COMPLETED);
        return ordersRepo.save(order);
    }
}

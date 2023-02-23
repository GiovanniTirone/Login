package com.example.login2.orders.repos;

import com.example.login2.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Order,Long> {

   // List<Order> findByCreatedBy(User user) ;

}

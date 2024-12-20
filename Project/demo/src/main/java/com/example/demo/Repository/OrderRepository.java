package com.example.demo.Repository;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer(Customer customer);
}

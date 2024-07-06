package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<List<Order>> findOrdersByUserUserid(int userID, PageRequest of);
    Optional<List<Order>> findOrdersByStatus(String status);
    Long countOrderByUserUserid(int userID);
}

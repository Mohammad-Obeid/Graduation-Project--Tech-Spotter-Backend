package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Model.orderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface orderItemRepository extends JpaRepository<orderItems,Integer> {
    Optional<List<orderItems>> findByOrder(Order order);
    Optional<orderItems> findByOrderOrderIDAndProductId(int orderId, int prodID);
}

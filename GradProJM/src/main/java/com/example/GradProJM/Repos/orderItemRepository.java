package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.orderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderItemRepository extends JpaRepository<orderItems,Integer> {
}

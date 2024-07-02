package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItems,Integer> {
    Optional<CartItems> findByProductIdAndCartID(int prodId, int cartID);
}

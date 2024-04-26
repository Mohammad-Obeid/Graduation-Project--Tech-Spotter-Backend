package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<product, Integer> {
    Optional<product> findByproductBarcode(String productBarcode);
}

package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<product, Integer> {
}

package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.customerRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerFeedbackRepository extends JpaRepository<customerRates,Integer> {
}

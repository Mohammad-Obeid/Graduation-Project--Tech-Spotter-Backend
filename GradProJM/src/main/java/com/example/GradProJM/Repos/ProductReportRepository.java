package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReportRepository extends JpaRepository<ProductReport,Integer> {
    Optional<List<ProductReport>> findProductReportByReportStatus(String status);
}

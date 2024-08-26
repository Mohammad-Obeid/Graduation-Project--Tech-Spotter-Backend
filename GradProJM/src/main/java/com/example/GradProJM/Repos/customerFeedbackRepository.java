package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.customerRates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface customerFeedbackRepository extends JpaRepository<customerRates,Integer> {
    Optional<List<customerRates>> findByProductsShopShopNameAndProductsProductProductBarcodeAndCustomerUserid(String name, String barcode, int userID);
}

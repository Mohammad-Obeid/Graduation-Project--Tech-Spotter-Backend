package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface productShopRepository extends JpaRepository<Shop_Products,Integer> {
    Optional<List> findShop_ProductsByShop_ShopID(int shopID);
}

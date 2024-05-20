package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Shop_Products;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface productShopRepository extends JpaRepository<Shop_Products,Integer> {
    Optional<List> findShop_ProductsByShop_ShopID(int shopID);
    Optional<Shop_Products> findShop_ProductsByShop_ShopIDAndProduct_ProductId(int shopID,int ProdID);

    Optional<Shop_Products> findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(String shpName, String brcode);
    Optional<List<Shop_Products>> findShop_ProductsByProductProductNameStartingWith(String prodName, Sort by);
    Optional<List<Shop_Products>> findShop_ProductsByProductProductNameStartingWith(String prodName);
}

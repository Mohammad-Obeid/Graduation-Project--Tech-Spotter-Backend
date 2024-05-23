package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Shop_Products;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface productShopRepository extends JpaRepository<Shop_Products,Integer> {
    Optional<List> findShop_ProductsByShop_ShopID(int shopID);
    Optional<List<Shop_Products>> findShop_ProductsByShop_ShopID(int shopID, PageRequest of);

    Page<Shop_Products> findShop_ProductsByShop_ShopID(int shopID, Pageable pageable);

//    Optional<Shop_Products> findShop_ProductsByShop_ShopIDAndProduct_ProductId(int shopID,int ProdID);

    Optional<Shop_Products> findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(String shpName, String brcode);
    Page<Shop_Products> findShop_ProductsByProductProductNameStartingWith(String prodName, Pageable pageable);
    Optional<List<Shop_Products>> findShop_ProductsByProductProductNameStartingWith(String prodName,PageRequest of);

}

package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Shop_Products;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;

public interface productShopRepository extends JpaRepository<Shop_Products,Integer> {
    Optional<List> findShop_ProductsByShop_ShopIDAndDeletedFalse(int shopID);

    Optional<List<Shop_Products>> findShop_ProductsByShop_ShopIDAndDeletedFalse(int shopID, PageRequest of);
    Optional<List<Shop_Products>> findShop_ProductsByShop_ShopIDAndProductProductCategoryAndDeletedFalse(int shopID,String category, PageRequest of);

    Page<Shop_Products> findShop_ProductsByShop_ShopIDAndDeletedFalse(int shopID, Pageable pageable);

//    Optional<Shop_Products> findShop_ProductsByShop_ShopIDAndProduct_ProductId(int shopID,int ProdID);

    Optional<Shop_Products> findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(String shpName, String brcode);
    Page<Shop_Products> findShop_ProductsByProductProductNameStartingWith(String prodName, Pageable pageable);
    Optional<List<Shop_Products>> findShop_ProductsByProductProductNameStartingWith(String prodName,PageRequest of);
    Long countByShopShopIDAndProductProductCategoryAndDeletedFalse(int shopID, String cat);
    Long countByProductProductNameStartingWithAndDeletedFalse(String name);
    Long countByShopShopIDAndDeletedFalse(int shopID);
    @Query("SELECT sp FROM Shop_Products sp ORDER BY sp.numOfSales DESC")
    List<Shop_Products> findTop10ByOrderByNumOfSalesDesc();

    @Query("SELECT sp FROM Shop_Products sp ORDER BY sp.productPublishDate DESC")
    List<Shop_Products> findTop10ByOrderByProductPublishDate();

    default Optional<List<Shop_Products>> findTopByNumOfSales() {
        List<Shop_Products> topProducts = findTop10ByOrderByNumOfSalesDesc();
        return topProducts.size() > 0 ? Optional.of(topProducts.subList(0, Math.min(topProducts.size(), 8))) : Optional.empty();
    }

    default Optional<List<Shop_Products>> findTopByDate() {
        List<Shop_Products> topProducts = findTop10ByOrderByProductPublishDate();
        return topProducts.size() > 0 ? Optional.of(topProducts.subList(0, Math.min(topProducts.size(), 8))) : Optional.empty();
    }

//    Optional<List> findShop_ProductsByProductProductNameAndDeletedFalse(String prodName);
    Optional<List<Shop_Products>> findShop_ProductsByProductProductNameStartingWithAndDeletedFalse(String prodName, PageRequest of);


}

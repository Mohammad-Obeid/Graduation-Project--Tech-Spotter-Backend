package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Model.orderItems;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface orderItemRepository extends JpaRepository<orderItems,Integer> {
    Optional<List<orderItems>> findByOrder(Order order);

    Optional<List<orderItems>> findAllByProduct_ShopShopID(int shopID, PageRequest of);
    Optional<List<orderItems>> findAllByProduct_ShopShopIDAndOrderItemStats(int shopID, String statss, PageRequest of);

    Optional<orderItems> findByOrderOrderIDAndProductId(int orderId, int prodID);

    Long countByProduct_ShopShopName(String shopName);
    Long countByProduct_ShopShopNameAndOrderItemStats(String shopName,String status);
    Optional<List<orderItems>> findByProductShopShopNameAndOrderOrderDate(String shopName, String orderdate);
}

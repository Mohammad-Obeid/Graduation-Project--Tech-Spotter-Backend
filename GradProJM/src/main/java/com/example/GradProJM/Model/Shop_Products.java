package com.example.GradProJM.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="shop_product_table")
@JsonSerialize(using = Shop_Product_Serializer.class)
public class Shop_Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
//    @JsonIgnore
    private ShopOwner shop;

    @ManyToOne(cascade = CascadeType.MERGE)
    private product product;

    private int quantity;

    private double rate;


//    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Order order;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<orderItems> orderItem;


    public Shop_Products() {
    }

    public Shop_Products(int id, ShopOwner shop, product product, int quantity) {
        this.id = id;
        this.shop = shop;
        this.product = product;
        this.quantity = quantity;
    }

    public Shop_Products( ShopOwner shop, product product, int quantity) {
        this.shop = shop;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShopOwner getShop() {
        return shop;
    }

    public void setShop(ShopOwner shop) {
        this.shop = shop;
    }

    public product getProduct() {
        return product;
    }

    public void setProduct(product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }




    //    public List<orderItems> getOrderItem() {
//        return orderItem;
//    }
//
//    public void setOrderItem(List<orderItems> orderItem) {
//        this.orderItem = orderItem;
//    }

    //    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }


}

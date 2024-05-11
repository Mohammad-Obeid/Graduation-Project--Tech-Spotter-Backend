package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="shopowner")
public class ShopOwner{
    @Id
//    @SequenceGenerator(
//            name = "shopOwner_sequence",
//            sequenceName = "shopOwner_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int shopID;
    private String shopName;
    private double shopRate;
    private int NumOfRates;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;


//    @OneToOne(mappedBy = "shop", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Order_item order_item;

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(name = "shop_product_table",
//            joinColumns = {
//                    @JoinColumn(name = "shopID",referencedColumnName = "shopID"),
//
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "productID", referencedColumnName = "productID")
//            }
//    )
//    private List<product> products;


    @OneToMany(mappedBy = "shop",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Shop_Products> shopProducts;

    public ShopOwner() {
    }

    public ShopOwner(int shopID, String shopName, double shopRate, int NumOfRates) {
        this.shopID = shopID;
        this.shopName = shopName;
        this.shopRate = 0;
        this.NumOfRates=NumOfRates;
    }

    public ShopOwner(String shopName, double shopRate, int NumOfRates) {
        this.shopName = shopName;
        this.shopRate = shopRate;
        this.NumOfRates=NumOfRates;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }



    public double getShopRate() {
        return shopRate;
    }

    public void setShopRate(double shopRate) {
        this.shopRate = shopRate;
    }

    public int getNumOfRates() {
        return NumOfRates;
    }

    public void setNumOfRates(int numOfRates) {
        NumOfRates = numOfRates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Shop_Products> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<Shop_Products> shopProducts) {
        this.shopProducts = shopProducts;
    }

//    public Order_item getOrder_item() {
//        return order_item;
//    }
//
//    public void setOrder_item(Order_item order_item) {
//        this.order_item = order_item;
//    }

    @Override
    public String toString() {
        return "ShopOwner{" +
                "shopID=" + shopID +
                ", ShopName='" + shopName + '\'' +
                ", shopRate=" + shopRate +
                ", NumOfRates=" + NumOfRates +
                '}';
    }
}
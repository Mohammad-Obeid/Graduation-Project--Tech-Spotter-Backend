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
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int shopID;
    private String ShopName;
    private String subStartDate,subEndDate;
    private double shopRate;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;


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

    public ShopOwner(int shopID, String shopName, String subStartDate, String subEndDate, double shopRate) {
        this.shopID = shopID;
        this.ShopName = shopName;
        this.subStartDate = subStartDate;
        this.subEndDate = subEndDate;
        this.shopRate = shopRate;
    }

    public ShopOwner(String shopName, String subStartDate, String subEndDate, double shopRate) {
        this.ShopName = shopName;
        this.subStartDate = subStartDate;
        this.subEndDate = subEndDate;
        this.shopRate = shopRate;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        this.ShopName = shopName;
    }


    public String getSubStartDate() {
        return subStartDate;
    }

    public void setSubStartDate(String subStartDate) {
        this.subStartDate = subStartDate;
    }

    public String getSubEndDate() {
        return subEndDate;
    }

    public void setSubEndDate(String subEndDate) {
        this.subEndDate = subEndDate;
    }

    public double getShopRate() {
        return shopRate;
    }

    public void setShopRate(double shopRate) {
        this.shopRate = shopRate;
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

    @Override
    public String toString() {
        return "ShopOwner{" +
                "shopID=" + shopID +
                ", ShopName='" + ShopName + '\'' +
                ", subStartDate=" + subStartDate +
                ", subEndDate=" + subEndDate +
                ", shopRate=" + shopRate +
                '}';
    }

}
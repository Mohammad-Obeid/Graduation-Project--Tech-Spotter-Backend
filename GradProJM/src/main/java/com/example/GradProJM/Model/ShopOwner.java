package com.example.GradProJM.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private User user;


    public ShopOwner() {
    }

    public ShopOwner(int shopID, String shopName, String subStartDate, String subEndDate, double shopRate) {
        this.shopID = shopID;
        ShopName = shopName;
        this.subStartDate = subStartDate;
        this.subEndDate = subEndDate;
        this.shopRate = shopRate;
    }

    public ShopOwner(String shopName, String subStartDate, String subEndDate, double shopRate) {
        ShopName = shopName;
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

    public String getsubStartDate() {
        return subStartDate;
    }

    public void setsubStartDate(String subStartDate) {
        this.subStartDate = subStartDate;
    }

    public String getsubEndDate() {
        return subEndDate;
    }

    public void setsubEndDate(String subEndDate) {
        this.subEndDate = subEndDate;
    }

    public double getshopRate() {
        return shopRate;
    }

    public void setshopRate(double shopRate) {
        this.shopRate = shopRate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

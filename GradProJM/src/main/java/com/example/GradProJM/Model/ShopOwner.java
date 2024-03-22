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
    private LocalDate SubStartDate,SubEndDate;
    private double ShopRate;

    @OneToOne
    private User user;

    public ShopOwner() {
    }

    public ShopOwner(int shopID, String shopName, LocalDate subStartDate, LocalDate subEndDate, double shopRate) {
        this.shopID = shopID;
        ShopName = shopName;
        SubStartDate = subStartDate;
        SubEndDate = subEndDate;
        ShopRate = shopRate;
    }

    public ShopOwner(String shopName, LocalDate subStartDate, LocalDate subEndDate, double shopRate) {
        ShopName = shopName;
        SubStartDate = subStartDate;
        SubEndDate = subEndDate;
        ShopRate = shopRate;
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
        ShopName = shopName;
    }

    public LocalDate getSubStartDate() {
        return SubStartDate;
    }

    public void setSubStartDate(LocalDate subStartDate) {
        SubStartDate = subStartDate;
    }

    public LocalDate getSubEndDate() {
        return SubEndDate;
    }

    public void setSubEndDate(LocalDate subEndDate) {
        SubEndDate = subEndDate;
    }

    public double getShopRate() {
        return ShopRate;
    }

    public void setShopRate(double shopRate) {
        ShopRate = shopRate;
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
                ", SubStartDate=" + SubStartDate +
                ", SubEndDate=" + SubEndDate +
                ", ShopRate=" + ShopRate +
                '}';
    }
}

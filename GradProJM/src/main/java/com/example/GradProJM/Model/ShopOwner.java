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

//    public ShopOwner(String shopName, LocalDate subStartDate, LocalDate subEndDate, double shopRate, String userName, String userPNum, String userEmail, String userPass, String userAddID) {
//        super(userName, userPNum, userEmail, userPass, userAddID);
//        ShopName = shopName;
//        SubStartDate = subStartDate;
//        SubEndDate = subEndDate;
//        ShopRate = shopRate;
//    }

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

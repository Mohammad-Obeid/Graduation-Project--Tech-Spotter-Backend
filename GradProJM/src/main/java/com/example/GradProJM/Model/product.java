package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName, productBarcode;
    private double productPrice;
    private String productCategory,productPublishDate, productDescription;
    private int numOfRates;
    private double productRate;
    @OneToMany(mappedBy = "product",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Shop_Products> shopProducts;


//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JsonIgnore
//    private Order_item orderItem;


    public product() {
    }

    public product(int productId, String productName, String productBarcode, double productPrice, String productCategory, String productPublishDate, String productDescription,
                   double rate, int numOfRates) {
        this.productId = productId;
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productPublishDate = productPublishDate;
        this.productDescription = productDescription;
        this.productRate=0;
        this.numOfRates=0;
    }
    public product(String productName, String productBarcode, double productPrice, String productCategory, String productPublishDate, String productDescription,
                   double rate, int numOfRates) {
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productPublishDate = productPublishDate;
        this.productDescription = productDescription;
        this.productRate=0;
        this.numOfRates=0;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPublishDate() {
        return productPublishDate;
    }

    public void setProductPublishDate(String productPublishDate) {
        this.productPublishDate = productPublishDate;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getNumOfRates() {
        return numOfRates;
    }

    public void setNumOfRates(int numOfRates) {
        this.numOfRates = numOfRates;
    }

    public double getProductRate() {
        return productRate;
    }

    public void setProductRate(double productRate) {
        this.productRate = productRate;
    }


    public List<Shop_Products> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<Shop_Products> shopProducts) {
        this.shopProducts = shopProducts;
    }


//    public List<Order_item> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<Order_item> orderItems) {
//        this.orderItems = orderItems;
//    }

    @Override
    public String toString() {
        return "product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productBarcode='" + productBarcode + '\'' +
                ", productPrice=" + productPrice +
                ", productCategory='" + productCategory + '\'' +
                ", productPublishDate='" + productPublishDate + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", numOfRates=" + numOfRates +
                ", productRate=" + productRate +
                '}';
    }
}

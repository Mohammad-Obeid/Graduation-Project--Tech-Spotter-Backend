package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName, productBarcode;
    private String productCategory;
    @OneToMany(mappedBy = "product",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Shop_Products> shopProducts;





    public product() {
    }

    public product(int productId, String productName, String productBarcode,String productCategory
                  ) {
        this.productId = productId;
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productCategory = productCategory;

    }
    public product(String productName, String productBarcode, String productCategory ,
                   double rate, int numOfRates) {
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productCategory = productCategory;

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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
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
                ", productCategory='" + productCategory + '\'' +
                '}';
    }
}

package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;
    private String productName, productBarcode;
    private double productPrice;
    private String productCategory,productPublishDate, productDescription;
//    @ManyToMany(mappedBy = "products",cascade = CascadeType.MERGE)
//    @JsonBackReference
//    @JsonIgnore
//    private List<ShopOwner> shopOwners;

    @OneToMany(mappedBy = "product",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Shop_Products> shopProducts;



    public product() {
    }

    public product(int productId, String productName, String productBarcode, double productPrice, String productCategory, String productPublishDate, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productPublishDate = productPublishDate;
        this.productDescription = productDescription;
    }
    public product(String productName, String productBarcode, double productPrice, String productCategory, String productPublishDate, String productDescription) {
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productPublishDate = productPublishDate;
        this.productDescription = productDescription;
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


    public List<Shop_Products> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<Shop_Products> shopProducts) {
        this.shopProducts = shopProducts;
    }

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
                '}';
    }
}

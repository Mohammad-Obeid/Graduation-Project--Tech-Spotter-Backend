package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productId;
    private String productName, productBarcode;
    private double productPrice;
    private String productCategory,productPublishDate, productDescription;
    private int productQuantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private ShopOwner shopOwner;

    public product() {
    }

    public product(int productId, String productName, String productBarcode, double productPrice, String productCategory, String productPublishDate, String productDescription, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productPublishDate = productPublishDate;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
    }
    public product(String productName, String productBarcode, double productPrice, String productCategory, String productPublishDate, String productDescription, int productQuantity) {
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productPublishDate = productPublishDate;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public ShopOwner getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(ShopOwner shopOwner) {
        this.shopOwner = shopOwner;
    }

    public boolean isAvaibleProduct(product prod){
        if(prod.getProductQuantity()>0)return true;
        return false;
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
                ", productQuantity=" + productQuantity +
                '}';
    }
}

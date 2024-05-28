package com.example.GradProJM.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="shop_product_table")
@JsonSerialize(using = Shop_Product_Serializer.class)
public class Shop_Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private ShopOwner shop;

    @ManyToOne(cascade = CascadeType.MERGE)
    private product product;

    private int quantity;

    private double rate=0;
    private int numOfRates=0;

    private double productPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductImageData img;

    private boolean deleted=false;

    private String productDescription;
    private String productPublishDate  = String.valueOf(LocalDateTime.now());

    private int numOfSales=0;


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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public ProductImageData getImg() {
        return img;
    }

    public void setImg(ProductImageData img) {
        this.img = img;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPublishDate() {
        return productPublishDate;
    }

    public void setProductPublishDate(String productPublishDate) {
        this.productPublishDate = productPublishDate;
    }

    public int getNumOfRates() {
        return numOfRates;
    }

    public void setNumOfRates(int numOfRates) {
        this.numOfRates = numOfRates;
    }

    public int getNumOfSales() {
        return numOfSales;
    }

    public void setNumOfSales(int numOfSales) {
        this.numOfSales = numOfSales;
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

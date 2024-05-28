package com.example.GradProJM.Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class Shop_Product_Serializer extends JsonSerializer<Shop_Products> {
    @Override
    public void serialize(Shop_Products shopProducts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("ShopName", shopProducts.getShop().getShopName());
        jsonGenerator.writeObjectField("ProductBarcode", shopProducts.getProduct().getProductBarcode());
        jsonGenerator.writeObjectField("ProductName", shopProducts.getProduct().getProductName());
        jsonGenerator.writeObjectField("ProductCompanyName", shopProducts.getProduct().getProductCompanyName());
        jsonGenerator.writeObjectField("ProductRate", shopProducts.getRate());
        jsonGenerator.writeObjectField("ProductPrice", shopProducts.getProductPrice());
        jsonGenerator.writeObjectField("ProductQuantity", shopProducts.getQuantity());
        jsonGenerator.writeObjectField("ProductCategory", shopProducts.getProduct().getProductCategory());
        jsonGenerator.writeObjectField("ProductDescription", shopProducts.getProductDescription());
        jsonGenerator.writeObjectField("ProductPublishDate", shopProducts.getProductPublishDate());
        jsonGenerator.writeObjectField("NumOfSales", shopProducts.getNumOfSales());
        jsonGenerator.writeEndObject();
    }
}

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
        jsonGenerator.writeObjectField("Product Barcode", shopProducts.getProduct().getProductBarcode());
        jsonGenerator.writeObjectField("Product Name", shopProducts.getProduct().getProductName());
        jsonGenerator.writeObjectField("Product Rate", shopProducts.getProduct().getProductRate());
        jsonGenerator.writeObjectField("Product Price", shopProducts.getProductPrice());
        jsonGenerator.writeObjectField("Product Quantity", shopProducts.getQuantity());

        jsonGenerator.writeEndObject();




    }
}

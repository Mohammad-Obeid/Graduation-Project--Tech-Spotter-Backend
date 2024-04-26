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

        jsonGenerator.writeObjectField("Shop", shopProducts.getShop());
        jsonGenerator.writeObjectField("Products", shopProducts.getProduct());

        jsonGenerator.writeEndObject();




    }
}

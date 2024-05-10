package com.example.GradProJM.Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.example.GradProJM.Model.User;
import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("userid", user.getUserid());
        jsonGenerator.writeStringField("userName", user.getUserName());
        jsonGenerator.writeStringField("userPNum", user.getUserPNum());
        jsonGenerator.writeStringField("userEmail", user.getUserEmail());
        jsonGenerator.writeNumberField("status", user.getStatus());

        if (user.getStatus() == 0) {
            jsonGenerator.writeObjectField("customer", user.getCustomer());
        }
        else{
            jsonGenerator.writeObjectField("shopOwner", user.getShopowner());
//            jsonGenerator.writeObjectField("products", user.getShopowner().getProducts());
        }
        jsonGenerator.writeObjectField("payments", user.getPaymentMethods());
        jsonGenerator.writeObjectField("address", user.getAddress());
        jsonGenerator.writeEndObject();
    }
}

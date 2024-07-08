package com.example.GradProJM.Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportSerializer extends JsonSerializer<ProductReport> {
    @Override
    public void serialize(ProductReport productReport, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("reportID", productReport.getId());
        reportData.put("Title", productReport.getReportTitle());
        reportData.put("content", productReport.getReportContent());
        reportData.put("status", productReport.getReportStatus());
        reportData.put("shopName", productReport.getProduct().getShop().getShopName());
        reportData.put("productBarcode", productReport.getProduct().getProduct().getProductBarcode());
        reportData.put("customerID", productReport.getUser().getCustomer().getCustID());
        jsonGenerator.writeObjectField("Report", reportData);

//        jsonGenerator.writeEndObject();

    }
}

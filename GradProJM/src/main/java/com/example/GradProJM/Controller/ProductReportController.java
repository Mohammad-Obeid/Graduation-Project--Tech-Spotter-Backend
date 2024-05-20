package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.ProductReport;
import com.example.GradProJM.Services.ProductReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("report")
public class ProductReportController {

    private final ProductReportService repService;

    public ProductReportController(ProductReportService repService) {
        this.repService = repService;
    }

    @PostMapping("submitReport")
    public ResponseEntity<ProductReport> SubmitNewReport(@RequestBody ProductReport rep){
        Optional<ProductReport> report= Optional.ofNullable(repService.SubmitNewReport(rep));
        return report.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}

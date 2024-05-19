package com.example.GradProJM.Controller;

import com.example.GradProJM.Services.ProductImgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/productImage")
public class ProductImageController {
    private final ProductImgService service;

    public ProductImageController(ProductImgService service) {
        this.service = service;
    }


    @PostMapping("/{prodBarcode}/{shopName}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file
                                         ,@PathVariable("prodBarcode") String prodBarcode
                                         , @PathVariable("shopName") String shopName) throws IOException {
        String uploadImage = service.uploadImage(file,prodBarcode, shopName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{prodBarcode}")
    public ResponseEntity<?> downloadImage(@PathVariable String prodBarcode) {
        byte[] imageData = service.downloadImage(prodBarcode);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
}

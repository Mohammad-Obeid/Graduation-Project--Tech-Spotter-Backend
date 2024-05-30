package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.ProductImageData;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Services.ProductImgService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productImage")
public class ProductImageController {
    private final ProductImgService service;

    public ProductImageController(ProductImgService service) {
        this.service = service;
    }


    @PostMapping("/{prodBarcode}/{shopName}")
    public ResponseEntity<?> uploadImage(@RequestParam List<MultipartFile> images,
                                         @PathVariable("prodBarcode") String prodBarcode,
                                         @PathVariable("shopName") String shopName
                                         ) throws IOException {
        Optional<List<ProductImageData>> uploadImage = Optional.of(service.saveImagesForProduct(images, shopName, prodBarcode));
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("getImages/{prodBarcode}/{shopName}")
    public ResponseEntity<List<String>> getImagesForProduct(@PathVariable("prodBarcode") String prodBarcode,
                                          @PathVariable("shopName") String shopName) {
        List<String> base64Images = service.getImagesForProduct(prodBarcode,shopName);
        return new ResponseEntity<>(base64Images, HttpStatus.OK);
    }
}

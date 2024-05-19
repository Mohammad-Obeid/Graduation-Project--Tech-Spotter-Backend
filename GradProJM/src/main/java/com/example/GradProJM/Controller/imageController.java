package com.example.GradProJM.Controller;
import com.example.GradProJM.Model.ImageData;
import com.example.GradProJM.Repos.StorageRepository;
import com.example.GradProJM.Services.StorageService;
import com.example.GradProJM.Utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/image")
public class imageController {
    private final StorageService service;

    public imageController(StorageService service) {
        this.service = service;
    }

    @PostMapping("/{shopName}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file
            , @PathVariable("shopName") String shopName) throws IOException {
        String uploadImage = service.uploadImage(file, shopName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }



}
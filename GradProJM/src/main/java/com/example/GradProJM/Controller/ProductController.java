package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.product;
import com.example.GradProJM.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService prodService;
    @Autowired
    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }
    @GetMapping("getAllProducts")
    public ResponseEntity<List> getAllProducts(){
        Optional<List> products= Optional.ofNullable(prodService.getAllProducts());
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("getProduct/{prodID}")
    public ResponseEntity<product> getAllProducts(@PathVariable("prodID") int prodID){
        Optional<product> product= Optional.ofNullable(prodService.getProduct(prodID));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("addNewProduct")
    public ResponseEntity<product> AddNewProduct(@RequestBody product prod){
        Optional<product> product= Optional.ofNullable(prodService.AddNewProduct(prod));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }
    @PatchMapping("updateProduct/{productID}")
    public ResponseEntity<product> UpdateProduct(@PathVariable("productID") int prodID,@RequestBody product productup){
        Optional<product> product= Optional.ofNullable(prodService.UpdateProduct(prodID, productup));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @DeleteMapping("deleteProduct/{prodID}")
    public ResponseEntity<Boolean> DeletePoductByID(@PathVariable("prodID") int prodID)
    {
        Optional<Boolean> product= Optional.ofNullable(prodService.DeletePoductByID(prodID));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }

    @DeleteMapping("deleteProductByBarcode/{prodBarcode}")
    public ResponseEntity<Boolean> DeletePoductByID(@PathVariable("prodBarcode") String prodBarcode)
    {
        Optional<Boolean> product= Optional.ofNullable(prodService.DeletePoductByBarcode(prodBarcode));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }

    @PatchMapping("RateaProduct/{custID}/{prodID}")
    public ResponseEntity<product> RateAProduct(@PathVariable("custID") int custID, @PathVariable("prodID") int prodID,@RequestBody double rate){
        Optional<product> product= Optional.ofNullable(prodService.RateAProduct(custID,prodID,rate));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}

package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.customerRates;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Services.ProductService;
import com.example.GradProJM.Services.ProductShopService;
import com.example.GradProJM.Services.shopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("productShop")
public class ProductShopController {
    private final ProductShopService prdShopService;


    @Autowired
    public ProductShopController(ProductShopService prdShopService) {
        this.prdShopService = prdShopService;
    }



    @GetMapping("getProductsForaShop/{shopID}")
    public ResponseEntity<List> getAllProductsForAShop(@PathVariable("shopID") int shopID){
        Optional<List> products= Optional.ofNullable(prdShopService.getAllProductsForAShop(shopID));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("getProducts/{shopID}/{pageNum}")
    public ResponseEntity<List<Shop_Products>> Search(@PathVariable("shopID") int shopID,
                                                      @PathVariable("pageNum") int pageNum
    ){
        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.view(shopID, pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("sortasc/{shopID}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortASC(@PathVariable("shopID") int shopID,
                                                       @PathVariable("field") String field,
                                                       @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.SortASC(shopID,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("sortDesc/{shopID}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortDESC(@PathVariable("shopID") int shopID,
                                                       @PathVariable("field") String field,
                                                       @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.sortDESC(shopID,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }



    @PostMapping("AddanExistingProductToShop")
    public ResponseEntity<Boolean> AddAnExistingProducttoaShop(@RequestBody Shop_Products shopProducts){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.AddAnExistingProducttoaShop(shopProducts));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @PostMapping("AddanExistingProductbyBarcodeToShop")
    public ResponseEntity<Boolean> AddAnExistingProductByBarcodetoaShop(@RequestBody Shop_Products shopProducts){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.AddAnExistingProductByBarcodetoaShop(shopProducts));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(false));
    }



    @PostMapping("AddanNewProductToaShop")
    public ResponseEntity<Boolean> AddNewProducttoaShop(@RequestBody Shop_Products shopProducts){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.AddNewProducttoaShop(shopProducts));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @DeleteMapping("deleteAProductFromAShop/{shopID}/{prodID}")
    public ResponseEntity<Boolean> DeleteAProductFromAShop(@PathVariable("shopID") int shopID,@PathVariable("prodID") int prodID){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.DeleteAProductFromAShop(shopID,prodID));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @DeleteMapping("deleteAProductByBarcodeFromAShop/{shopID}/{prodBarcode}")
    public ResponseEntity<Boolean> deleteAProductByBarcodeFromAShop(@PathVariable("shopID") int shopID,@PathVariable("prodBarcode") String prodBarcode){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.deleteAProductByBarcodeFromAShop(shopID,prodBarcode));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @PostMapping("makeNewOrder")
    public ResponseEntity<Order> makeNewOrder(@RequestBody Order order){
        Optional<Order> ord= Optional.ofNullable(prdShopService.MakeNewOrder(order));
        return ord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("rateAProduct")
    public ResponseEntity<Shop_Products> rateAProduct(@RequestBody customerRates custRate){

//        @PathVariable("custID") int custID,
//        @PathVariable("shopName") String shopName,
//        @PathVariable("prodBarcode") String prodBarcode,
//        @PathVariable("rate") double rate
        Optional<Shop_Products> productRate= Optional.ofNullable(prdShopService.rateAProduct(
                custRate));
        return productRate.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("rateAProduct/{custID}/{shopName}/{prodBarcode}")
    public ResponseEntity<Shop_Products> rateAProduct(@PathVariable("custID") int custID,
                                                      @PathVariable("shopName") String shopName,
                                                      @PathVariable("prodBarcode") String prodBarcode,
                                                      @RequestBody customerRates custRate){


        Optional<Shop_Products> productRate= Optional.ofNullable(prdShopService.rateAProduct(custID,
                shopName,
                prodBarcode,
                custRate));
        return productRate.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


}

package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Services.shopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(path="shop")
@RestController
public class ShopOwnerController {

    private final shopOwnerService shopOwnerService;

    @Autowired
    public ShopOwnerController(shopOwnerService shopownerService) {
        this.shopOwnerService = shopownerService;
    }


    @GetMapping
    public List<ShopOwner> allShopOwners(){
        return shopOwnerService.getCustomers();
    }

    @PostMapping
    public void addNewShop(@RequestBody ShopOwner shopOwner){
        shopOwnerService.addNewShopOwner(shopOwner);
    }

    @GetMapping("/{id}")
    public ShopOwner getShopbyID(@PathVariable("id") int ShopID){
        return shopOwnerService.getShopOwnerbyId(ShopID);
    }

//    @PostMapping("addProducts/{shopID}")
//    public ResponseEntity<ShopOwner> AddProductsToSomeShop(@PathVariable("shopID") int shopID, @RequestBody product prod){
//        Optional<ShopOwner> shop= Optional.ofNullable(shopOwnerService.AddNewProduct(shopID, prod));
//        return shop.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.FOUND)
//                        .body(null));
//    }

//    @PatchMapping("modifyProduct/{shopOwnerID}/{productID}")
//    public ResponseEntity<ShopOwner> ModifyProductForShopOwner(@PathVariable("shopOwnerID") int shopOwnerID,
//                                                               @PathVariable("productID") int productID,
//                                                               @RequestBody product prod){
//        Optional<ShopOwner> shop= Optional.ofNullable(shopOwnerService.ModifyProductForShopOwner(shopOwnerID,productID, prod));
//        return shop.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                        .body(null));
//    }


//    @DeleteMapping("deleteProduct/{shopID}/{productID}")
//    public ResponseEntity<ShopOwner> DeleteProductForShopOwner(@PathVariable("shopID") int shopID,
//                                                               @PathVariable("productID") int productID){
//        Optional<ShopOwner> shop= Optional.ofNullable(shopOwnerService.DeleteProductForShopOwner(shopID,productID));
//        return shop.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(null));
//    }


    @PatchMapping("RateAShop/{custID}/{shopID}")
    public ResponseEntity<ShopOwner> RateAShop(@PathVariable("custID") int custID, @PathVariable("shopID") int shopID, @RequestBody double rate){
        Optional<ShopOwner> shop= Optional.ofNullable(shopOwnerService.RateAShop(custID,shopID,rate));
        return shop.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


}

package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.wishList;
import com.example.GradProJM.Services.wishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("wishlist")
public class wishListController {
    private final wishListService wishservice;

    public wishListController(wishListService wishservice) {
        this.wishservice = wishservice;
    }

    @GetMapping("getWishListItems/{custID}")
    public ResponseEntity<List> getWishListItemsForAUser(@PathVariable("custID") int custID){
        Optional<List> products= Optional.ofNullable(wishservice.getWishListItemsForAUser(custID));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("addProductToWishList/{custID}/{prodID}")
    public ResponseEntity<wishList> addProductToWishList(@PathVariable("custID") int custID,
                                                         @PathVariable("prodID") int prodID){
        Optional<wishList> addProduct= Optional.ofNullable(wishservice.addProductToWishList(custID, prodID));
        return addProduct.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("deleteProductFromWishList/{custID}/{prodID}")
    public ResponseEntity<wishList> removeProductFromWishList(@PathVariable("custID") int custID,
                                                         @PathVariable("prodID") int prodID){
        Optional<wishList> addProduct= Optional.ofNullable(wishservice.removeProductFromWishList(custID, prodID));
        return addProduct.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}

package com.example.GradProJM.Controller;
import com.example.GradProJM.Model.ShoppingCart;
import com.example.GradProJM.Services.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartService shpCartServ;

    public ShoppingCartController(ShoppingCartService shpCartServ) {
        this.shpCartServ = shpCartServ;
    }

    @GetMapping("getCartItems/{userID}")
    public ResponseEntity<List> getartItems(@PathVariable("userID") int userID){
        Optional<List> products= Optional.ofNullable(shpCartServ.getCartProducts(userID));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

//    @PostMapping("addProductToCart/{custID}/{productID}")
//    public ResponseEntity<ShoppingCart> AddProductToCustomerCart(@PathVariable("custID") int custID, @PathVariable("productID") int prodID){
//        Optional<ShoppingCart> cart= Optional.ofNullable(shpCartServ.AddProductToCart(custID,prodID));
//        return cart.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(null));
//    }

    @PostMapping("addProductToCart/{userID}/{shopName}/{productBarcode}")
    public ResponseEntity<ShoppingCart> AddProductToCustomerCart(@PathVariable("userID") int userID,
                                                                 @PathVariable("shopName") String shopName,
                                                                 @PathVariable("productBarcode") String prodBarcode){
        Optional<ShoppingCart> cart= Optional.ofNullable(shpCartServ.AddProductToCart(userID, shopName, prodBarcode));
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }
    @DeleteMapping("deleteProductFromACart/{userID}/{shopName}/{productBarcode}")
    public ResponseEntity<ShoppingCart> DeleteAProductFromACart(@PathVariable("userID") int userID,
                                                                @PathVariable("shopName") String shopName,
                                                                @PathVariable("productBarcode") String prodBarcode){
        Optional<ShoppingCart> deleteProd= Optional.ofNullable(shpCartServ.DeleteProductFromCart(userID, shopName, prodBarcode));
        return deleteProd.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}

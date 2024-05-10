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

    @GetMapping("getCartItems/{custID}")
    public ResponseEntity<List> getartItems(@PathVariable("custID") int custID){
        Optional<List> products= Optional.ofNullable(shpCartServ.getCartProducts(custID));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("addProductToCart/{custID}/{productID}")
    public ResponseEntity<ShoppingCart> AddProductToCustomerCart(@PathVariable("custID") int custID, @PathVariable("productID") int prodID){
        Optional<ShoppingCart> cart= Optional.ofNullable(shpCartServ.AddProductToCart(custID,prodID));
        return cart.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @DeleteMapping("deleteProductFromACart/{custID}/{productID}")
    public ResponseEntity<ShoppingCart> DeleteAProductFromACart(@PathVariable("custID") int custID, @PathVariable("productID") int prodID){
        Optional<ShoppingCart> deleteProd= Optional.ofNullable(shpCartServ.DeleteProductFromCart(custID,prodID));
        return deleteProd.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}

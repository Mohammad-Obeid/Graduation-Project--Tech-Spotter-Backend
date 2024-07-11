package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("admin")

public class AdminController {
    private final AdminService adminsrv;

    public AdminController(AdminService adminsrv) {
        this.adminsrv = adminsrv;
    }

    @GetMapping("getReports")
    public ResponseEntity<List<ProductReport>> getAllAcceptedReports(){
        Optional<List<ProductReport>> reports= Optional.ofNullable(adminsrv.getAllAcceptedReports());
        return reports.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("AcceptReport/{repID}")
    public ResponseEntity<ProductReport> changeReportStatus(@PathVariable("repID") int repID){
        Optional<ProductReport> report= Optional.ofNullable(adminsrv.acceptReport(repID));
        return report.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("getPendingReports")
    public ResponseEntity<List<ProductReport>> getPendingReports(){
        Optional<List<ProductReport>> reports= Optional.ofNullable(adminsrv.getPendingReports());
        return reports.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @DeleteMapping("deleteProductfromShop/{shopName}/{prodBarcode}")
    public ResponseEntity<ShopOwner> DeleteProductfromShop(@PathVariable("shopName") String shopName,
                                                               @PathVariable("prodBarcode") String prodBarcode){
        Optional<ShopOwner> product= Optional.ofNullable(adminsrv.DeleteProductfromShop(shopName, prodBarcode));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("deleteProduct/{prodBarcode}")
    public ResponseEntity<product> DeleteProductfromShop(@PathVariable("prodBarcode") String prodBarcode){
        Optional<product> product= Optional.ofNullable(adminsrv.DeleteProduct(prodBarcode));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("deleteUser/{userEmail}")
    public ResponseEntity<User> DeleteUserbyEmail(@PathVariable("userEmail") String userEmail){
        Optional<User> user= Optional.ofNullable(adminsrv.DeleteUserbyEmail(userEmail));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("deleteUserID/{userID}")
    public ResponseEntity<User> DeleteUserByID(@PathVariable("userID") int userID){
        Optional<User> user= Optional.ofNullable(adminsrv.DeleteUserByID(userID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

//    @DeleteMapping("removeAddress/{addID}")
//    public ResponseEntity<Address> DeleteAddress(@PathVariable("addID") int addID){
//        return null;
//    }

    @DeleteMapping("cancelOrder/{orderID}")
    public ResponseEntity<Order> canceOrder(@PathVariable("orderID") int orderID){
        Optional<Order> order= Optional.ofNullable(adminsrv.cancelOrder(orderID));
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }



    @GetMapping("getNewUsers/{month}")
    public ResponseEntity<List<Sales>> getNewUsers(@PathVariable("month") String month){
        Optional<List<Sales>> sales= Optional.ofNullable(adminsrv.getNewUsers(month));
        return sales.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("create")
    public ResponseEntity<User> getNewUsers(@RequestBody User user){
        Optional<User> admin= Optional.ofNullable(adminsrv.CreateNewAdmin(user));
        return admin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}

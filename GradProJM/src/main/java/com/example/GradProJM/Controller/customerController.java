package com.example.GradProJM.Controller;
import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ProductReport;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Services.customerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(path="customer")
@RestController
public class customerController {
    private final customerService customerService;


    @Autowired
    public customerController(customerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public List<Customer> std(){
        return customerService.getCustomers();
    }

    @PostMapping
    public void addNewCustomer(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerbyID(@PathVariable("id") int custID){
        Customer cust=customerService.getCustomerbyId(custID);
        return cust;
    }

    @GetMapping("search/{prodName}/{pageNum}")
    public ResponseEntity<List<Shop_Products>> Search(@PathVariable("prodName") String prodName,
                                                      @PathVariable("pageNum") int pageNum
    ){
        Optional<List<Shop_Products>> products= Optional.ofNullable(customerService.Search(prodName, pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("sortasc/{prodName}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortASC(@PathVariable("prodName") String prodName,
                                       @PathVariable("field") String field,
                                       @PathVariable("pageNum") int pageNum
                                                      ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(customerService.SortASC(prodName,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }



    @GetMapping("sortdesc/{prodName}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortDESC(@PathVariable("prodName") String prodName,
                                                       @PathVariable("field") String field,
                                                        @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(customerService.SortDESC(prodName,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }



}

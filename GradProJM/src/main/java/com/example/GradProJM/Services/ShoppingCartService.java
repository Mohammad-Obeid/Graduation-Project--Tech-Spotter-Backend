package com.example.GradProJM.Services;


import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.ShoppingCart;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShoppingCartRepository;
import com.example.GradProJM.Repos.productShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shpCrtRepo;
    private final CustomerRepository custRepo;
    private final ProductRepository prodRepo;
    private final productShopRepository prodshpRepo;


    public ShoppingCartService(ShoppingCartRepository shpCrtRepo,
                               CustomerRepository custRepo,
                               ProductRepository prodRepo,
                               productShopRepository prodshpRepo) {
        this.shpCrtRepo = shpCrtRepo;
        this.custRepo=custRepo;
        this.prodRepo=prodRepo;
        this.prodshpRepo=prodshpRepo;
    }


    public List getCartProducts(int custID) {
        Optional<Customer> customer=custRepo.findCustomerByCustID(custID);
        if(customer.isPresent()){
            ShoppingCart shpcrt=customer.get().getShoppingCart();
            return shpcrt.getProducts();
        }
        return null;
    }

    public ShoppingCart AddProductToCart(int custID, int prodID) {
        Optional<Customer> customer=custRepo.findCustomerByCustID(custID);
        if(customer.isPresent()){
            ShoppingCart shpcrt=customer.get().getShoppingCart();
            Optional<Shop_Products> prod=prodshpRepo.findById(prodID);
            if(prod.isPresent()){
                double totprice= shpcrt.getTotalPrice();
                totprice+=prod.get().getProduct().getProductPrice();
                List<Shop_Products> products=shpcrt.getProducts();
                int flag=0;
                for(int i=0;i<products.size();i++){
                    if(products.get(i).getProduct().getProductId()==prodID)flag=1;
                }
                if(flag==0){
                products.add(prod.get());
                shpcrt.setProducts(products);
                shpcrt.setTotalPrice(totprice);
                shpCrtRepo.save(shpcrt);
                return shpcrt;}
                return null;
            }
            return null;
        }
        return null;
    }

    public ShoppingCart DeleteProductFromCart(int custID, int prodID) {
        Optional<Customer> customer=custRepo.findCustomerByCustID(custID);
        if(customer.isPresent()) {
            ShoppingCart shpcrt = customer.get().getShoppingCart();
            Optional<product> prod = prodRepo.findById(prodID);
            if (prod.isPresent()) {
                double totPrice=0;
                totPrice=shpcrt.getTotalPrice();
                totPrice-=prod.get().getProductPrice();
                List<Shop_Products> products=shpcrt.getProducts();
                products.remove(prod.get());
                shpcrt.setTotalPrice(totPrice);
                shpcrt.setProducts(products);
                shpCrtRepo.save(shpcrt);
                return shpcrt;

            }

            return null;
        }
        return null;
    }
}

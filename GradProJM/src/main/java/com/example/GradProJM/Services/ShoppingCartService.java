package com.example.GradProJM.Services;


import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ShoppingCart;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shpCrtRepo;
    private final CustomerRepository custRepo;
    private final ProductRepository prodRepo;

    public ShoppingCartService(ShoppingCartRepository shpCrtRepo,
                               CustomerRepository custRepo,
                               ProductRepository prodRepo) {
        this.shpCrtRepo = shpCrtRepo;
        this.custRepo=custRepo;
        this.prodRepo=prodRepo;
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
            Optional<product> prod=prodRepo.findById(prodID);
            if(prod.isPresent()){
                double totprice= shpcrt.getTotalPrice();
                totprice+=prod.get().getProductPrice();
                List<product> products=shpcrt.getProducts();
                int flag=0;
                for(int i=0;i<products.size();i++){
                    if(products.get(i).getProductId()==prodID)flag=1;
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
                List<product> products=shpcrt.getProducts();
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

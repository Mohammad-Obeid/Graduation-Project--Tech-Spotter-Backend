package com.example.GradProJM.Services;


import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductRepository;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository prodRepo;
    private final CustomerRepository custRepo;
    public ProductService(ProductRepository prodRepo, CustomerRepository custRepo) {
        this.prodRepo=prodRepo;
        this.custRepo=custRepo;
    }

    public List<product> getAllProducts() {
        List<product> prods= (prodRepo.findAll());
        if(prods.isEmpty())return null;
        return prods;
    }

    public product getProduct(int prodID) {
        Optional<product> prod=prodRepo.findById(prodID);
        return prod.orElse(null);
    }

    public product AddNewProduct(product prod) {
        Optional<product> prd=prodRepo.findByproductBarcode(prod.getProductBarcode());
        if(!prd.isPresent()){
            prodRepo.save(prod);
            return prod;
        }
        return null;
    }

    public product UpdateProduct(int prodID, product product) {
        Optional<product> prod=prodRepo.findById(prodID);
        if(prod.isPresent()){
            Optional<product> prdchk=prodRepo.findByproductBarcode(product.getProductBarcode());
            if(prdchk.isPresent() && !prdchk.get().getProductBarcode().equals(prod.get().getProductBarcode())){
                return null;
            }
            product.setProductId(prodID);
            prodRepo.save(product);
            return product;
        }
        return null;
    }

    public Boolean DeletePoductByID(int prodID) {
        Optional<product> prod=prodRepo.findById(prodID);
        if(prod.isPresent()){
            prodRepo.deleteById(prodID);
            return true;
        }
        return null;
    }

    public Boolean DeletePoductByBarcode(String prodBarcode) {
        Optional<product> prdchk=prodRepo.findByproductBarcode(prodBarcode);
        if(prdchk.isPresent()){
            prodRepo.deleteById(prdchk.get().getProductId());
            return true;
        }
        return null;
    }

    public product RateAProduct(int custID, int prodID,double rate) {
        Optional<Customer> customer = custRepo.findCustomerByCustID(custID);
        if (customer.isPresent()) {
            Optional<product> prod = prodRepo.findById(prodID);
            if (prod.isPresent()) {
                int numOfRates = prod.get().getNumOfRates();
                double rat = prod.get().getProductRate();
                rat *= numOfRates;
                rat += rate;
                numOfRates += 1;
                rat /= numOfRates;
                DecimalFormat df = new DecimalFormat("#.#");
                rat = Double.parseDouble(df.format(rat));
                prod.get().setNumOfRates(numOfRates);
                prod.get().setProductRate(rat);
                prodRepo.save(prod.get());
                return prod.get();
            }
            return null;
        }
    return null;
    }
}

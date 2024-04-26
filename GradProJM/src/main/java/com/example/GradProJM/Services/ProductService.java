package com.example.GradProJM.Services;


import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static ProductRepository prodRepo;
    @Autowired
    public ProductService(ProductRepository prodRepo) {
        this.prodRepo=prodRepo;
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
}

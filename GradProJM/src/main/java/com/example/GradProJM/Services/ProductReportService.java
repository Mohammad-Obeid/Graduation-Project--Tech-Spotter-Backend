package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductReportRepository;
import com.example.GradProJM.Repos.productShopRepository;
import com.example.GradProJM.Repos.userRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductReportService {
    private final ProductReportRepository repRepo;
    private final CustomerRepository custRepo;

    private final productShopRepository prdshpRepo;
    private final userRepository userRepo;

    public ProductReportService(ProductReportRepository repRepo, CustomerRepository custRepo, productShopRepository prdshpRepo, userRepository userRepo) {
        this.repRepo = repRepo;
        this.custRepo = custRepo;
        this.prdshpRepo = prdshpRepo;
        this.userRepo = userRepo;
    }

    public ProductReport SubmitNewReport(ProductReport rep) {
        Optional<User> user = userRepo.findById(rep.getUser().getUserid());
        if(user.isPresent() && user.get().getStatus()==0) {
            Optional<Customer> cust = custRepo.findCustomerByCustID(user.get().getCustomer().getCustID());
            if (cust.isPresent()) {
                Optional<Shop_Products> prod = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndDeletedFalse(
                        rep.getProduct().getShop().getShopName(),
                        rep.getProduct().getProduct().getProductBarcode()
                );
                if (prod.isPresent()) {
                    rep.setReportStatus("Pending Report");
                    rep.setProduct(prod.get());
                    rep.setUser(user.get());
                    repRepo.save(rep);
                    return rep;
                }
            }
        }
        return null;
    }
}

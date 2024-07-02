package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ProductReport;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductReportRepository;
import com.example.GradProJM.Repos.productShopRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductReportService {
    private final ProductReportRepository repRepo;
    private final CustomerRepository custRepo;

    private final productShopRepository prdshpRepo;

    public ProductReportService(ProductReportRepository repRepo, CustomerRepository custRepo, productShopRepository prdshpRepo) {
        this.repRepo = repRepo;
        this.custRepo = custRepo;
        this.prdshpRepo = prdshpRepo;
    }

    public ProductReport SubmitNewReport(ProductReport rep) {
        Optional<Customer> cust = custRepo.findCustomerByCustID(rep.getCust().getCustID());
        if(cust.isPresent()){
            Optional<Shop_Products> prod = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndAndDeletedFalse(
                    rep.getProduct().getShop().getShopName(),
                    rep.getProduct().getProduct().getProductBarcode()
            );
            if(prod.isPresent()){
                rep.setReportStatus("Pending Report");
                rep.setProduct(prod.get());
                rep.setCust(cust.get());
                repRepo.save(rep);
                return rep;
            }
         }
        return null;
    }
}

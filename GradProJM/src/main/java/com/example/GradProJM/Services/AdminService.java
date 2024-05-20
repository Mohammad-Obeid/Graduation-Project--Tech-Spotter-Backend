package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService
{
    private final AdminRepository adminRepo;
    private final ProductReportRepository reportRepo;
    private final productShopRepository shpprdRepo;
    private final ShopOwnerRepository shpRepo;
    private final ProductRepository prdRepo;
    private final userRepository userRepo;
    private final OrderRepository orderRepo;
    private final CustomerRepository custRepo;
    private final EmailService emailService;

    public AdminService(AdminRepository adminRepo,
                        ProductReportRepository reportRepo,
                        productShopRepository shpprdRepo,
                        ShopOwnerRepository shpRepo,
                        EmailService emailService,
                        ProductRepository prdRepo,
                        userRepository userRepo,
                        OrderRepository orderRepo,
                        CustomerRepository custRepo



                        ) {
        this.adminRepo = adminRepo;
        this.reportRepo=reportRepo;
        this.shpprdRepo=shpprdRepo;
        this.shpRepo=shpRepo;
        this.emailService=emailService;
        this.prdRepo=prdRepo;
        this.userRepo=userRepo;
        this.orderRepo=orderRepo;
        this.custRepo=custRepo;
    }

    public List<ProductReport> getAllAcceptedReports() {
        Optional<List<ProductReport>> reports = reportRepo.findProductReportByReportStatus("Processing Report");
        return reports.orElse(null);
    }

    public List<ProductReport> getPendingReports() {
        Optional<List<ProductReport>> reports = reportRepo.findProductReportByReportStatus("Pending Report");
        return reports.orElse(null);
    }


    public ShopOwner DeleteProductfromShop(String shopName, String prodBarcode) {
        Optional<Shop_Products> prod = shpprdRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName,prodBarcode);
        prod.ifPresent(shpprdRepo::delete);
        Optional<ShopOwner> shp = shpRepo.findShopOwnerByShopName(shopName);
//        emailService.sendDeletionEmailForShop(shp.get().getShopName(),shp.get().getUser().getUserEmail(),prodBarcode);
        if(prod.isPresent() && shp.isPresent()){
            shp.ifPresent(shopOwner -> emailService.sendDeletionEmailForShop(shopOwner.getShopName(), "mohammadkadoumi77@yahoo.com", prodBarcode));
        return shp.get();}
        return null;
    }

    public product DeleteProduct(String prodBarcode) {
        Optional<product> prod = prdRepo.findByproductBarcode(prodBarcode);
        if(prod.isPresent()){
            prdRepo.delete(prod.get());
            return prod.get();
        }
        return null;
    }

    public User DeleteUserbyEmail(String userEmail) {
        Optional<User> user = userRepo.findByuserEmail(userEmail);
        if(user.isPresent()){
            userRepo.delete(user.get());
            return user.get();
        }
        return null;
    }


    public User DeleteUserByID(int userID) {
        Optional<User> user = userRepo.findByuserid(userID);
        return DeleteUserbyEmail(user.get().getUserEmail());
    }

    public Order cancelOrder(int orderID) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
//            orderRepo.delete(order.get());
            for(int i = 0; i < order.get().getOrderItem().size(); i++) {
                emailService.sendOrderDeletionEmailForShop(order.get().getOrderItem().get(i).getProduct()
                , order.get().getCustomer());
            }
            return order.get();
        }
        return null;
    }

    public ProductReport acceptReport(int repID) {
        Optional<ProductReport> rep = reportRepo.findById(repID);
        if(rep.isPresent()){
            rep.get().setReportStatus("Processing Report");
            Optional<Customer> cust = custRepo.findCustomerByCustID(rep.get().getCust().getCustID());
            if(cust.isPresent()) {
                emailService.sendReportAcceptionEmail(cust, rep);
                reportRepo.save(rep.get());
                return rep.get();
            }
        }
        return null;
    }
}

package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService
{
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
        Optional<Shop_Products> prod = shpprdRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndDeletedFalse(shopName,prodBarcode);
        Optional<ShopOwner> shp = shpRepo.findShopOwnerByShopName(shopName);
        if(prod.isPresent() && shp.isPresent()){
            prod.get().setDeleted(true);
            shpprdRepo.save(prod.get());
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
                , order.get().getUser().getCustomer());
                order.get().getOrderItem().get(i).setOrderItemStats("Cancelled");
            }
            return order.get();
        }
        return null;
    }

    public ProductReport acceptReport(int repID) {
        Optional<ProductReport> rep = reportRepo.findById(repID);
        if(rep.isPresent()){
            rep.get().setReportStatus("Processing Report");
            Optional<Customer> cust = custRepo.findCustomerByCustID(rep.get().getUser().getCustomer().getCustID());
            if(cust.isPresent()) {
                emailService.sendReportAcceptionEmail(cust, rep);
                reportRepo.save(rep.get());
                return rep.get();
            }
        }
        return null;
    }

    public List<Sales> getNewUsers(String month) {
        String x = "2024-"+month;
        Optional<List<User>> users = userRepo.findByJoinDateStartingWith(x);
        List<Sales> usercount = new ArrayList<>();
        for(int i = 0 ; i < users.get().size();i++){
            LocalDateTime time = LocalDateTime.parse(users.get().get(i).getJoinDate());
            Sales ss = new Sales();
            ss.setDay(time.getDayOfMonth());
            if(usercount.isEmpty()){
                ss.setTotalPrice(1);
                usercount.add(ss);
            }
            else{
                int flag=0;
                for(int j = 0; j < usercount.size();j++){
                    if(usercount.get(j).getDay()==time.getDayOfMonth()){
                        usercount.get(j).setTotalPrice(usercount.get(j).getTotalPrice()+1);
                        flag=1;
                    }
                }
                if(flag==0){
                    ss.setTotalPrice(1);
                    usercount.add(ss);
                }
            }
//            usercount.add(users.get().get(i).get);
//            System.out.println(users.get().get(i).getUserName()+"\n\n");
//            System.out.println("/////////////////////////");
//            System.out.println("/////////////////////////");
//            System.out.println("/////////////////////////");

        }
        return usercount;
    }

    public User CreateNewAdmin(User user) {
        Optional<User> user1 = userRepo.findByuserEmail(user.getUserEmail());
        if(user1.isPresent()){
            return null;
        }
        BCryptPasswordEncoder bcr = new BCryptPasswordEncoder();
        user.setUserPass(bcr.encode(user.getUserPass()));
        user.setRole(Role.ADMIN);
        user.setStatus(2);
        user.setJoinDate(String.valueOf(LocalDateTime.now()));
        Admin ad = new Admin();
        ad.setAdminName(user.getAdmin().getAdminName());
        ad.setUser(user);
        user.setAdmin(ad);
        user.setVerified(true);
        userRepo.save(user);
        return user;

    }


    //todo: make paging for all methods
}

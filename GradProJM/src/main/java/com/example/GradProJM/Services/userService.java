package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class userService {
    private final userRepository userRepo;
    private static userRepository userRepo1 ;
    private static String g = "";

    private final AddressRepository addRepo;

    private final PaymentMethodRepository paymentMetRepo;

    private final PaymentSpecificationRepository paySpecRepo;
    private final ShoppingCartRepository shpCrtRepo;
    private final tempRepository tempRepository;
    private static tempRepository tempRepo;

    @Autowired
    public userService(userRepository userRepo,
                       AddressRepository addRepo,
                       PaymentMethodRepository paymentMetRepo,
                       PaymentSpecificationRepository paySpecRepo,
                       ShoppingCartRepository shpCrtRepo,
                       tempRepository tempRepository,
                       userRepository userRepo1,
                       tempRepository tempRepo) {
        this.userRepo = userRepo;
        this.addRepo = addRepo;
        this.paymentMetRepo = paymentMetRepo;
        this.paySpecRepo = paySpecRepo;
        this.shpCrtRepo = shpCrtRepo;
        this.tempRepository = tempRepository;
        this.userRepo1=userRepo1;
        this.tempRepo=tempRepo;
    }


    @Autowired
    private EmailService emailService;

    private RandomCodeGenerator randomCode = new RandomCodeGenerator();

    public static void removeUnVerifiedUsers() {
        LocalDateTime current = LocalDateTime.now();
        List<User> users = userRepo1.findAll();
        for(int i=0; i < users.size(); i++){
            LocalDateTime userjoinDate = LocalDateTime.parse(users.get(i).getJoinDate()).plusMinutes(10);
            if(!users.get(i).isVerified() && current.isAfter(userjoinDate)){
                userRepo1.delete(users.get(i));
            }
        }
    }

    public static void removeUnVerifiedUsersCodes() {
        LocalDateTime current = LocalDateTime.now();
        List <tempDataBaseForVerificationCode> temps = tempRepo.findAll();
        for(int i=0; i < temps.size(); i++){
            LocalDateTime userjoinDate = LocalDateTime.parse(temps.get(i).getTime()).plusMinutes(10);
            if(current.isAfter(userjoinDate)){
                tempRepo.delete(temps.get(i));
            }
        }
    }

    public List<User> getUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty())
            return null;
        return users;
    }

    public User getUserByEmail(String email) {
        Optional<User> userr = userRepo.findByuserEmail(email);
        if (userr.isPresent()) {
            if (!userr.get().isVerified()) return userr.get();
        }
        return null;
    }

    public User SendEmailVerification(User user) {
        Optional<User> findByuserEmail = userRepo.findByuserEmail(user.getUserEmail());
        if (findByuserEmail.isPresent() && findByuserEmail.get().isVerified()) {
            return null;
        } else if (findByuserEmail.isEmpty()) {
            randomCodeGenerator();
            sendVerificationCodeMail(user);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = bCryptPasswordEncoder.encode(user.getUserPass());
            user.setUserPass(encryptedPassword);
            user.setJoinDate(String.valueOf(LocalDateTime.now()));
//            user.setVerified(false);
            if (user.getStatus() == 0) {
                Customer cust = new Customer();
                cust = user.getCustomer();
                user.setCustomer(cust);
                cust.setUser(user);
            } else if (user.getStatus() == 1) {
                ShopOwner shop = new ShopOwner();
                shop = user.getShopowner();
                user.setShopowner(shop);
                shop.setUser(user);
            }
            userRepo.save(user);
            //todo: make the temporaryDataBase to save each email with its verification Code
            tempDataBaseForVerificationCode tmp = new tempDataBaseForVerificationCode(user.getUserEmail(), g, String.valueOf(LocalTime.now()));
            Optional<tempDataBaseForVerificationCode> tmpchk = tempRepository.findByUserEmail(user.getUserEmail());
            if (tmpchk.isPresent()) {
                tmpchk.get().setVerificationCode(g);
                tmpchk.get().setTime(String.valueOf(LocalDateTime.now()));
                tempRepository.save(tmpchk.get());
            } else
                tempRepository.save(tmp);
            return user;
        }
        return findByuserEmail.get();
    }
//    public void addUser(String code){
//        if(code.equals(g)){
//            userRepo.saveAndFlush(user1);
//        }
//    }

    public void addUser(User user) {

        Optional<tempDataBaseForVerificationCode> tmp = tempRepository.findByUserEmail(user.getUserEmail());
        if (tmp.isPresent()) {
            if (user.getUserEmail().equals(tmp.get().getUserEmail())) {
//                user.setJoinDate(String.valueOf(LocalDate.now()));
                user.setVerified(true);
                userRepo.saveAndFlush(user);
                Optional<tempDataBaseForVerificationCode> tmpchk = tempRepository.findByUserEmail(user.getUserEmail());
                tempRepository.delete(tmpchk.get());

            }
        }
    }

    public void removetempCheck(String Email) {
        Optional<tempDataBaseForVerificationCode> tmpchk = tempRepository.findByUserEmail(Email);
        tempRepository.delete(tmpchk.get());
    }

    public ShoppingCart GetShoppingCart() {
        ShoppingCart shpCrt = new ShoppingCart(0);
        shpCrtRepo.save(shpCrt);
        return shpCrt;

    }


    public User getUserbyId(int userid) {
        Optional<User> user = userRepo.findByuserid(userid);
        if (user.isPresent()) {
            if (user.get().getStatus() == 1) {
//                user.get().
            }
            return user.get();
        }
        return null;
    }

    //    @EventListener(ApplicationReadyEvent.class)
    public void sendVerificationCodeMail(User user) {
        emailService.sendVerificationCodeEmail("mohammadkadoumi77@yahoo.com", g, user.getUserName());//riyadjannah2023@gmail.com
//        1200644@student.birzeit.edu
    }

    public String randomCodeGenerator() {
        g = randomCode.GenerateCode();
        return g;
    }

    public String getCode(String email) {
//        return g;
        Optional<tempDataBaseForVerificationCode> tmp = tempRepository.findByUserEmail(email);
        if (tmp.isPresent()) return tmp.get().getVerificationCode();
        return " ";
    }

    public String getTime(String email) {
//        return g;
        Optional<tempDataBaseForVerificationCode> tmp = tempRepository.findByUserEmail(email);
        if (tmp.isPresent()) return tmp.get().getTime();
        return "";
    }

    public Optional<User> DeleteUser(int userID) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            userRepo.deleteById(userID);
            return user;
        }
        return null;
    }

    public User AddNewAddress(int userID, Address address) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            List<Address> addresses = user.get().getAddress();
            addresses.add(address);
            address.setUser(user.get());
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }

    public User RemoveAddress(int userID, String addName) {
        Optional<User> user = userRepo.findByuserid(userID);
        int flag = 0;
        if (user.isPresent()) {
            List<Address> addresses = user.get().getAddress();
            for (int i = 0; i < addresses.size(); i++) {
                if (addresses.get(i).getCity().equals(addName)) {
                    addRepo.deleteById(addresses.get(i).getAddID());
                    addresses.remove(i);
                    System.out.println(addresses.get(i));
                    flag = 1;
                }
            }
            if (flag == 0) {
                return null;
            } else {
                user.get().setAddress(addresses);
                userRepo.saveAndFlush(user.get());
                return user.get();
            }

        }
        return null;
    }

    public User updateAddress(int userID, int addID, Address add) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            List<Address> addresses = user.get().getAddress();
            int flag = 0;
            for (int i = 0; i < addresses.size(); i++) {
                if (addresses.get(i).getAddID() == addID) {
                    addresses.get(i).setCity(add.getCity());
                    addresses.get(i).setCountry(add.getCountry());
                    addresses.get(i).setState(add.getState());
                    addresses.get(i).setStreet(add.getStreet());
                    addresses.get(i).setPostalCode(add.getPostalCode());
                    flag = 1;
                    user.get().setAddress(addresses);
                    userRepo.save(user.get());
                }
            }
            if (flag == 0)
                return null;
            return user.get();
        }
        return null;
    }

    public User updateCustomerInformation(int userID, User userr) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            Customer cust = user.get().getCustomer();
            cust.setFName(userr.getCustomer().getFName());
            cust.setLName(userr.getCustomer().getLName());
            cust.setBDate(userr.getCustomer().getBDate());
            user.get().setCustomer(cust);
            user.get().setUserid(userID);

            if (!userr.getUserEmail().toLowerCase().trim().equals(user.get().getUserEmail().toLowerCase().trim())) {
                Optional<User> findUser = userRepo.findByuserEmail(userr.getUserEmail());
                if (findUser.isPresent())
                    user.get().setUserEmail(user.get().getUserEmail());
                else user.get().setUserEmail(userr.getUserEmail());
            } else {
                user.get().setUserEmail(userr.getUserEmail());
            }
            user.get().setUserPass(userr.getUserPass());
            user.get().setUserName(userr.getUserName());
            user.get().setUserPNum(userr.getUserPNum());
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }

    public User updateShopOwnerInformation(int userID, User userr) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            ShopOwner shopOwner = user.get().getShopowner();
            shopOwner.setShopName(userr.getShopowner().getShopName());
            user.get().setShopowner(shopOwner);
            user.get().setUserid(userID);
            if (!userr.getUserEmail().toLowerCase().trim().equals(user.get().getUserEmail().toLowerCase().trim())) {
                Optional<User> findUser = userRepo.findByuserEmail(userr.getUserEmail());
                if (findUser.isPresent())
                    user.get().setUserEmail(user.get().getUserEmail());
                else user.get().setUserEmail(userr.getUserEmail());
            } else {
                user.get().setUserEmail(userr.getUserEmail());
            }
            user.get().setUserPass(userr.getUserPass());
            user.get().setUserName(userr.getUserName());
            user.get().setUserPNum(userr.getUserPNum());
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }

    public User AddNewPaymentMethod(int userID, PaymentMethods paymentMethod) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            List<PaymentMethods> paymentMethods = user.get().getPaymentMethods();
            paymentMethod.setUser(user.get());
            paymentMethodSpecification paymentspec = paymentMethod.getPaymentMethodSpecification();
            paymentspec.setPaymentMethod(paymentMethod);
            paymentspec.setCardNum(paymentMethod.getPaymentMethodSpecification().getCardNum());
            paymentspec.setExpireDate(paymentMethod.getPaymentMethodSpecification().getExpireDate());
            paymentspec.setSecurityCode(paymentMethod.getPaymentMethodSpecification().getSecurityCode());
            paymentspec.setOwnerName(paymentMethod.getPaymentMethodSpecification().getOwnerName());
            paymentMetRepo.save(paymentMethod);
            paymentMethod.setPaymentMethodSpecification(paymentspec);
            user.get().setPaymentMethods(paymentMethods);
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }


    public User UpdatePaymentByPayNameForAUser(int userID, String paymentName, PaymentMethods paymentMethod) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            List<PaymentMethods> payments = user.get().getPaymentMethods();
            int flag = 0;
            for (int i = 0; i < payments.size(); i++) {
                if (payments.get(i).getPaymentMethodName().equals(paymentName)) {
                    flag = 1;
                    paymentMethodSpecification paymentspec = payments.get(i).getPaymentMethodSpecification();
                    paymentspec.setCardNum(paymentMethod.getPaymentMethodSpecification().getCardNum());
                    paymentspec.setExpireDate(paymentMethod.getPaymentMethodSpecification().getExpireDate());
                    paymentspec.setSecurityCode(paymentMethod.getPaymentMethodSpecification().getSecurityCode());
                    paymentspec.setOwnerName(paymentMethod.getPaymentMethodSpecification().getOwnerName());
                    paymentspec.setPaymentSpecID(payments.get(i).getPaymentMethodSpecification().getPaymentSpecID());
                    paymentspec.setPaymentMethod(payments.get(i));
                    payments.get(i).setPaymentMethodSpecification(paymentspec);
                    payments.get(i).setPaymentMethodName(paymentMethod.getPaymentMethodName());
                    payments.get(i).setUser(user.get());
                    userRepo.save(user.get());
                    break;
                }
            }
            if (flag == 0) return null;
            return user.get();
        } else {
            return null;
        }
    }

    public User UpdatePaymentByPayIDForAUser(int userID, int paymentID, PaymentMethods paymentMethod) {
        Optional<User> user = userRepo.findByuserid(userID);
        if (user.isPresent()) {
            List<PaymentMethods> payments = user.get().getPaymentMethods();
            int flag = 0;
            for (int i = 0; i < payments.size(); i++) {
                if (payments.get(i).getPaymentMethodId() == paymentID) {
                    flag = 1;
                    paymentMethodSpecification paymentspec = payments.get(i).getPaymentMethodSpecification();
                    paymentspec.setCardNum(paymentMethod.getPaymentMethodSpecification().getCardNum());
                    paymentspec.setExpireDate(paymentMethod.getPaymentMethodSpecification().getExpireDate());
                    paymentspec.setSecurityCode(paymentMethod.getPaymentMethodSpecification().getSecurityCode());
                    paymentspec.setOwnerName(paymentMethod.getPaymentMethodSpecification().getOwnerName());
                    paymentspec.setPaymentSpecID(payments.get(i).getPaymentMethodSpecification().getPaymentSpecID());
                    paymentspec.setPaymentMethod(payments.get(i));
                    payments.get(i).setPaymentMethodSpecification(paymentspec);
                    payments.get(i).setPaymentMethodName(paymentMethod.getPaymentMethodName());
                    payments.get(i).setUser(user.get());
                    userRepo.save(user.get());
                    break;
                }
            }
            if (flag == 0) return null;
            return user.get();
        }
        return null;
    }


//    public User DeletePaymentMethod(int userID,int paymentID) {
//        Optional<User> user=userRepo.findByuserid(userID);
//        if(user.isPresent()){
//            List<PaymentMethods> payments=user.get().getPaymentMethods();
//            int flag=0;
//            for(int i=0;i<payments.size();i++){
//                if(payments.get(i).getPaymentMethodId()==paymentID){
//                    flag=1;
//                    paymentMetRepo.deleteById(paymentID);
//                    payments.remove(i);
//                    user.get().setPaymentMethods(payments);
//                    userRepo.save(user.get());
//                    return user.get();
//                }
//            }
//            if(flag==0)return null;
//        }
//            return null;
//    }


    public User DeletePaymentMethod(int userID, int paymentID) {
        Optional<User> user = userRepo.findByuserid(userID);
        int flag = 0;
        if (user.isPresent()) {
            List<PaymentMethods> payments = user.get().getPaymentMethods();
            for (int i = 0; i < payments.size(); i++) {
                if (payments.get(i).getPaymentMethodId() == paymentID) {
                    flag = 1;
                    payments.get(i).setUser(null);
                    paymentMethodSpecification ps = payments.get(i).getPaymentMethodSpecification();
                    payments.get(i).setPaymentMethodSpecification(null);
                    ps.setPaymentMethod(null);
                    PaymentMethods pay = payments.get(i);
                    payments.remove(i);
                    user.get().setPaymentMethods(payments);
                    paymentMetRepo.deleteById(pay.getPaymentMethodId());
                    paySpecRepo.deleteById(ps.getPaymentSpecID());
                    break;
                }
                if (flag == 1) break;
            }
            if (flag == 0) return null;
            else {


                userRepo.saveAndFlush(user.get());
                return user.get();
            }
        }

        return null;
    }

//    public String Login(LoginRequest loginreq) {
//        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
//        Optional<User> user=userRepo.findUserByUserName(loginreq.getUserNameOrEmail());
//        if(user.isPresent()){
//            if(user.get().isVerified()){
//            user=userRepo.findByuserEmail(loginreq.getUserNameOrEmail());
//            if(user.isPresent()){
//                if(bCryptPasswordEncoder.matches(loginreq.getPassword(), user.get().getUserPass())){
//                    return "Success";
//                }
//                return "Password Wasn't correct";
//            }}
//            return "user Wasn't Found";
//        }
//        else{
//            if(user.get().isVerified()) {
//                if (bCryptPasswordEncoder.matches(loginreq.getPassword(), user.get().getUserPass())) {
//                    return "Success";
//                }
//                return "Password Wasn't correct";
//            }
//            return "user Wasn't Found";
//        }
//    }


    public String Login(LoginRequest loginreq) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        Optional<User> user=userRepo.findByuserEmail(loginreq.getUserNameOrEmail());
        if(user.isPresent()){
            if(user.get().isVerified()){
                if((loginreq.getUserNameOrEmail().equals(user.get().getUserEmail()) || loginreq.getUserNameOrEmail().equals(user.get().getUserName()))
                        && bCryptPasswordEncoder.matches(loginreq.getPassword(), user.get().getUserPass())){
                    return "Success";
                }
                else if((loginreq.getUserNameOrEmail().equals(user.get().getUserEmail()) || loginreq.getUserNameOrEmail().equals(user.get().getUserName()))
                        && !bCryptPasswordEncoder.matches(loginreq.getPassword(), user.get().getUserPass())){
                    return "Password Wasn't correct";
                }
//                return "lala";
            }
            return "User wasn't Found!!";

        }
        return "User wasn't Found!!";
    }

    public User getUserByEmaill(LoginRequest login) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        Optional<User> user = userRepo.findByuserEmail(login.getUserNameOrEmail());
        if(user.isPresent()){
            if(bCryptPasswordEncoder.matches(login.getPassword(), user.get().getUserPass())){
                return user.get();
            }
            return null;
        }
        return null;
    }
}
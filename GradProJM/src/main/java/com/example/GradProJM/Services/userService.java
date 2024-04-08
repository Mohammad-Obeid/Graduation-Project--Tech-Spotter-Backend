package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Address;
import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.AddressRepository;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {
    private final userRepository userRepo;
    private static String g="";
    private static User user1=new User();

    private final AddressRepository addRepo;
    @Autowired
    public userService(userRepository userRepo, AddressRepository addRepo) {
        this.userRepo = userRepo;
        this.addRepo = addRepo;
    }



    @Autowired
    private EmailService emailService;

    private RandomCodeGenerator randomCode=new RandomCodeGenerator();

    public List<User> getUsers(){
        List<User> users= userRepo.findAll();
        if(users.isEmpty())
            return null;
        return users;
    }

    public void SendEmailVerification(User user) {
        Optional<User> findByuserEmail=userRepo.findByuserEmail(user.getUserEmail());
        if(findByuserEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        else {
            user1=user;
            randomCodeGenerator();
            sendVerificationCodeMail();
//            if()
        }
    }
//    public void addUser(String code){
//        if(code.equals(g)){
//            userRepo.saveAndFlush(user1);
//        }
//    }

    public void addUser(User user){
        Optional<User> findByuserEmail=userRepo.findByuserEmail(user.getUserEmail());
        if(findByuserEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        userRepo.saveAndFlush(user1);
    }



    public User getUserbyId(int userid) {
        Optional<User> user = userRepo.findByuserid(userid);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void sendVerificationCodeMail(){
        emailService.sendVerificationCodeEmail("mohammadkadoumi77@yahoo.com", g,user1.getUserName());//riyadjannah2023@gmail.com
//        1200644@student.birzeit.edu
    }
    public String randomCodeGenerator(){
        g=randomCode.GenerateCode();
        return g;
    }

    public String getCode() {
        return g;
    }

    public void DeleteUser(int userID) {
        User user=getUserbyId(userID);
        if(user!=null){
            userRepo.deleteById(userID);
        }
    }

    public User AddNewUser(int userID, Address address) {
        Optional<User> user=userRepo.findByuserid(userID);
        if(user.isPresent()){
            List<Address> addresses=user.get().getAddress();
            addresses.add(address);
            address.setUser(user.get());
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }

    public User RemoveAddress(int userID, String addName) {
        Optional<User> user=userRepo.findByuserid(userID);
        int flag=0;
        if(user.isPresent()){
            List<Address> addresses=user.get().getAddress();
            for(int i=0;i<addresses.size();i++){
                if(addresses.get(i).getCity().equals(addName)){
                    System.out.println("kakakkakakak");
//                    addresses.get(i).setUser(null);
                    addRepo.deleteById(addresses.get(i).getAddID());
                    addresses.remove(i);
                    System.out.println(addresses.get(i));
                    flag=1;
                }
            }
            if(flag==0){
                return null;}
            else{
            user.get().setAddress(addresses);
            userRepo.saveAndFlush(user.get());
            return user.get();}

        }
        return null;
    }

    public User updateAddress(int userID, int addID, Address add) {
        Optional<User> user=userRepo.findByuserid(userID);
        if(user.isPresent()){
            List<Address> addresses= user.get().getAddress();
            int flag=0;
            for(int i=0;i<addresses.size();i++){
                if(addresses.get(i).getAddID()==addID){
                    addresses.get(i).setCity(add.getCity());
                    addresses.get(i).setCountry(add.getCountry());
                    addresses.get(i).setState(add.getState());
                    addresses.get(i).setStreet(add.getStreet());
                    addresses.get(i).setPostalCode(add.getPostalCode());
                    flag=1;
                    user.get().setAddress(addresses);
                    userRepo.save(user.get());
                }
            }
            if(flag==0)
                return null;
            return user.get();
        }
        return null;
    }

    public User updateCustomerInformation(int userID, User userr) {
        Optional<User> user= userRepo.findByuserid(userID);
        if(user.isPresent()){
            Customer cust=user.get().getCustomer();
            cust.setFName(userr.getCustomer().getFName());
            cust.setLName(userr.getCustomer().getLName());
            cust.setBDate(userr.getCustomer().getBDate());
            user.get().setCustomer(cust);
            user.get().setUserid(userID);
            user.get().setUserEmail(userr.getUserEmail());
            user.get().setUserPass(userr.getUserPass());
            user.get().setUserName(userr.getUserName());
            user.get().setUserPNum(userr.getUserPNum());
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }

    public User updateShopOwnerInformation(int userID, User userr) {
        Optional<User> user= userRepo.findByuserid(userID);
        if(user.isPresent()){
            ShopOwner shopOwner=user.get().getShopowner();
            shopOwner.setShopName(userr.getShopowner().getShopName());
            user.get().setShopowner(shopOwner);
            user.get().setUserid(userID);
            user.get().setUserEmail(userr.getUserEmail());
            user.get().setUserPass(userr.getUserPass());
            user.get().setUserName(userr.getUserName());
            user.get().setUserPNum(userr.getUserPNum());
            userRepo.save(user.get());
            return user.get();
        }
        return null;
    }
}



package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Address;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.AddressRepository;
import com.example.GradProJM.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private static AddressRepository addrepo;
    private static userRepository userRepo;
    @Autowired
    public AddressService(AddressRepository addrepo, userRepository userRepo) {
        this.addrepo = addrepo;
        this.userRepo=userRepo;
    }

    public List<Address> getAddresses(int userID) {
        Optional<User> user = userRepo.findByuserid(userID);
        if(user.isPresent()){
            System.out.println(user.get().getAddress());
            return user.get().getAddress();
        }
        else{
            throw new IllegalStateException("User with ID "+ userID + " wasn't Found");
        }
    }

    public void AddnewAddress(int userID,Address address) {
        Optional<User> user = userRepo.findByuserid(userID);


        if(user.isPresent()){
            address.setUser(user.get());
            addrepo.save(address);
            user.get().setAddress(address.getUser().getAddress());}
        else{
            throw new IllegalStateException("User with ID "+ userID + " wasn't Found");
        }
    }


    //todo: modify this method to remove address by name ... not ID
    public void deleteAddress(int userID, int addID) {
        Optional<User> user = userRepo.findByuserid(userID);
        if(user.isPresent()){
            addrepo.deleteById(addID);
        }
        else{
            throw new IllegalStateException("User with ID "+ userID + " wasn't Found");
        }
    }
}

package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    Optional<Address> findByGovernorateAndCityAndTownAndStreetNoAndDepNoAndUser_Userid(String gov,
                                                                              String city,
                                                                              String town,
                                                                              String stNo,
                                                                              String depNo,
                                                                                       int userID);
}

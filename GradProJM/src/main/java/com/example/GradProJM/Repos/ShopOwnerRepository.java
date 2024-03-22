package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOwnerRepository extends JpaRepository<ShopOwner,Integer> {
//    Optional<User> findByuserEmail(String userEmail);
//    Optional<User> findByuserid(int userid);
}
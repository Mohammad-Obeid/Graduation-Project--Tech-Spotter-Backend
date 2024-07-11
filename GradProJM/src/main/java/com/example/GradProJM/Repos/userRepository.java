package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User,Integer> {
    Optional<User> findByuserEmail(String userEmail);
    Optional<User> findByuserid(int userid);

    Optional<User> findUserByUserName(String userName);


    Optional<List<User>> findByJoinDateStartingWith(String joinDate);
}

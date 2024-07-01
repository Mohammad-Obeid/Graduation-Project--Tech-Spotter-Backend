package com.example.GradProJM.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
             SELECT t FROM Token t inner join User u\s
             on t.user.userid = u.userid\s 
            where u.userid = :id and (t.expired = false or t.revoked = false)\s
            """)

    List<Token> findAllValidTokenByUser(int id);



    Optional<Token> findByToken(String token);
}

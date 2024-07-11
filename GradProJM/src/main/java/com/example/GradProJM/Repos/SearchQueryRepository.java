package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchQueryRepository extends JpaRepository<SearchQuery,Integer> {
    Optional<List<SearchQuery>> findByCustID(int custID);
    Optional<SearchQuery> findByCustIDAndQuery(int custID, String query);

    @Query("SELECT sq FROM SearchQuery sq WHERE sq.custID = :custID ORDER BY sq.numOfClicks DESC")
    Optional<List<SearchQuery>> findByCustIDOrderByNumOfClicksDesc(@Param("custID") int custID);

    @Query("SELECT sq FROM SearchQuery sq ORDER BY sq.numOfClicks DESC")
    List<SearchQuery> findTop3ByNumOfClicks(Pageable pageable);


}

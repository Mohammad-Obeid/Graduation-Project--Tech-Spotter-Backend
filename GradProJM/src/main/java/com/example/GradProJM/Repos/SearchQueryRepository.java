package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchQueryRepository extends JpaRepository<SearchQuery,Integer> {
    Optional<List<SearchQuery>> findByCustID(int custID);
    List<SearchQuery> findByCustID(int custID, PageRequest searchDate);
    @Query("SELECT sq FROM SearchQuery sq WHERE sq.custID = :custID ORDER BY sq.searchDate ASC")
    Optional<SearchQuery> findFirstByCustIDOrderBySearchDateAsc(@Param("custID") int custID);
    void delete(SearchQuery searchQuery);
}

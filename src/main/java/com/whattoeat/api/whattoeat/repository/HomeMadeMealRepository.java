package com.whattoeat.api.whattoeat.repository;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeMadeMealRepository extends CrudRepository<HomeMadeMeal, String> {

    List<HomeMadeMeal> findByUserId(String userId);

    @Query(value = "select m.* from home_made_meal m, user u " +
            "where m.city = :city and m.country = :country and u.is_private = 0 and m.user_id = u.id " +
            "order by last_eaten_date desc " +
            "limit :limit",
            nativeQuery = true)
    List<HomeMadeMeal> findExplore(
            @Param("country") String country,
            @Param("city") String city,
            @Param("limit") int limit);
}

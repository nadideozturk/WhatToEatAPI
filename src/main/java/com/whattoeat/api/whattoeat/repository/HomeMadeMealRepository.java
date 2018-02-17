package com.whattoeat.api.whattoeat.repository;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeMadeMealRepository extends CrudRepository<HomeMadeMeal, String> {

    List<HomeMadeMeal> findByUserId(String userId);

}

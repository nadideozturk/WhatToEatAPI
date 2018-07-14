package com.whattoeat.api.whattoeat.repository;

import com.whattoeat.api.whattoeat.domain.OutsideMeal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OutsideMealRepository extends CrudRepository<OutsideMeal, String> {

    List<OutsideMeal> findByUserId(String userId);

}

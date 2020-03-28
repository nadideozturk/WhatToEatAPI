package com.whattoeat.api.whattoeat.repository;

import com.whattoeat.api.whattoeat.domain.CityCountry;
import org.springframework.data.repository.CrudRepository;

public interface CityCountryRepository extends CrudRepository<CityCountry, CityCountry.CityCountryKey> {
}

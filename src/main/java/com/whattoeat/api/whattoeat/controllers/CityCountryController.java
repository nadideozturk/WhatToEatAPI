package com.whattoeat.api.whattoeat.controllers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.whattoeat.api.whattoeat.domain.CityCountry;
import com.whattoeat.api.whattoeat.repository.CityCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RequestMapping("/citycountry")
@RestController
public class CityCountryController {

    private static String DUMMY_KEY = "dummyKey";

    private static LoadingCache<String, List<CityCountry>> cache;

    @Autowired
    private CityCountryRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CityCountry> getAll() throws ExecutionException {
        if (cache == null) {
            initializeCache();
        }
        return cache.get(DUMMY_KEY);
    }

    private void initializeCache() {
        final CacheLoader loader = new CacheLoader<String, List<CityCountry>>() {
            @Override
            public List<CityCountry> load(String s) {
                return (List<CityCountry>) repository.findAll();
            }
        };
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(7, TimeUnit.DAYS)
                .build(loader);
    }
}

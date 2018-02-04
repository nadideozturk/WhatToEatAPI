package com.whattoeat.api.whattoeat.controllers;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import com.whattoeat.api.whattoeat.dto.HomeMadeMealDTO;
import com.whattoeat.api.whattoeat.mapper.HomeMadeMealMapper;
import com.whattoeat.api.whattoeat.repository.HomeMadeMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
public class HomeMadeMealController {

    @Autowired
    private HomeMadeMealRepository repository;

    @RequestMapping("/homemademeals")
    public List<HomeMadeMealDTO> getAll(){


        HomeMadeMealMapper mapper = new HomeMadeMealMapper();
        List<HomeMadeMealDTO> list = new ArrayList<HomeMadeMealDTO>();
        repository.findAll().forEach(m -> { list.add(mapper.toDTO(m)); });
        return list;
    }
}

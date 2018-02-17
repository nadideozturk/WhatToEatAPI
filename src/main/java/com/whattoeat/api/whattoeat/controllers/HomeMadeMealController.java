package com.whattoeat.api.whattoeat.controllers;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import com.whattoeat.api.whattoeat.dto.HomeMadeMealDTO;
import com.whattoeat.api.whattoeat.exception.NotFoundException;
import com.whattoeat.api.whattoeat.mapper.HomeMadeMealMapper;
import com.whattoeat.api.whattoeat.repository.HomeMadeMealRepository;
import com.whattoeat.api.whattoeat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/homemademeals")
@RestController
public class HomeMadeMealController {

    @Autowired
    private HomeMadeMealRepository repository;

    @Autowired
    private HomeMadeMealMapper mapper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<HomeMadeMealDTO> getAll() {
        String userId = userService.getUserID();
        List<HomeMadeMealDTO> list = new ArrayList<HomeMadeMealDTO>();
        repository.findByUserId(userId).forEach(m -> {
            list.add(mapper.toDTO(m));
        });
        return list;
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.GET)
    public HomeMadeMealDTO getMeal(@PathVariable String mealId) {
        HomeMadeMeal meal = repository.findOne(mealId);
        if (meal == null) {
            throw new NotFoundException();
        }
        return mapper.toDTO(meal);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createMeal(@RequestBody HomeMadeMealDTO homeMadeMealDto){
        String userId = userService.getUserID();
        HomeMadeMeal meal = mapper.createFromDTO(homeMadeMealDto);
        meal.setUserId(userId);
        repository.save(meal);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateMeal(@RequestBody HomeMadeMealDTO homeMadeMealDto){
        HomeMadeMeal meal = mapper.fromDTO(homeMadeMealDto);
        HomeMadeMeal existingMeal = repository.findOne(meal.getId());
        if(existingMeal == null){
            throw new NotFoundException();
        }
        repository.save(meal);
    }

    @RequestMapping(value= "/{mealId}", method = RequestMethod.DELETE)
    public void deleteMeal(@PathVariable String mealId){
        HomeMadeMeal meal = repository.findOne(mealId);
        if(meal == null){
            throw new NotFoundException();
        }
        repository.delete(mealId);
    }
}

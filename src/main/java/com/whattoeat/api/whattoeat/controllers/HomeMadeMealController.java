package com.whattoeat.api.whattoeat.controllers;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import com.whattoeat.api.whattoeat.domain.User;
import com.whattoeat.api.whattoeat.dto.HomeMadeMealDTO;
import com.whattoeat.api.whattoeat.exception.AuthenticationException;
import com.whattoeat.api.whattoeat.exception.NotFoundException;
import com.whattoeat.api.whattoeat.mapper.HomeMadeMealMapper;
import com.whattoeat.api.whattoeat.repository.HomeMadeMealRepository;
import com.whattoeat.api.whattoeat.repository.UserRepository;
import com.whattoeat.api.whattoeat.service.ImageUploadService;
import com.whattoeat.api.whattoeat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RequestMapping("/homemademeals")
@RestController
public class HomeMadeMealController {

    @Autowired
    private HomeMadeMealRepository homeMadeMealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomeMadeMealMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUploadService imageUploadService;

    private final String IMAGE_FOLDER_NAME = "whattoeat/homemademeals";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<HomeMadeMealDTO> getAll() {
        String userId = userService.getUserID();
        List<HomeMadeMealDTO> list = new ArrayList<HomeMadeMealDTO>();
        homeMadeMealRepository.findByUserId(userId).forEach(m -> {
            list.add(mapper.toDTO(m));
        });
        list.sort(Comparator.comparing(HomeMadeMealDTO::getLastEatenDate).reversed());
        return list;
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.GET)
    public HomeMadeMealDTO getMeal(@PathVariable String mealId) {
        String userId = userService.getUserID();
        HomeMadeMeal meal = homeMadeMealRepository.findOne(mealId);
        if (meal == null) {
            throw new NotFoundException();
        }
        if (!meal.getUserId().equals(userId)) {
            throw new AuthenticationException();
        }
        return mapper.toDTO(meal);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createMeal(@RequestBody HomeMadeMealDTO homeMadeMealDto) {
        String userId = userService.getUserID();
        HomeMadeMeal meal = mapper.createFromDTO(homeMadeMealDto);
        meal.setUserId(userId);
        User user = userRepository.findOne(userId);
        meal.setCountry(user.getCountry());
        meal.setCity(user.getCity());

        if (!StringUtils.isEmpty(homeMadeMealDto.getPhotoContent())) {
            String imageUrl = imageUploadService.uploadImage(meal.getId(), homeMadeMealDto.getPhotoContent(), IMAGE_FOLDER_NAME);
            meal.setPhotoUrl(imageUrl);
        }
        if (meal.getLastEatenDate() == null) {
            meal.setLastEatenDate(new Date());
        }
        
        homeMadeMealRepository.save(meal);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateMeal(@RequestBody HomeMadeMealDTO homeMadeMealDto) {
        String userId = userService.getUserID();
        HomeMadeMeal meal = mapper.fromDTO(homeMadeMealDto);
        HomeMadeMeal existingMeal = homeMadeMealRepository.findOne(meal.getId());
        if (existingMeal == null) {
            throw new NotFoundException();
        }
        if (!existingMeal.getUserId().equals(userId)) {
            throw new AuthenticationException();
        }
        meal.setUserId(userId);
        if (!homeMadeMealDto.getPhotoContent().equals("Empty")) {
            String imageUrl = imageUploadService.uploadImage(meal.getId(), homeMadeMealDto.getPhotoContent(), IMAGE_FOLDER_NAME);
            meal.setPhotoUrl(imageUrl);
        }
        homeMadeMealRepository.save(meal);
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.DELETE)
    public void deleteMeal(@PathVariable String mealId) {
        HomeMadeMeal meal = homeMadeMealRepository.findOne(mealId);
        String userId = userService.getUserID();
        if (meal == null) {
            throw new NotFoundException();
        }
        if (!meal.getUserId().equals(userId)) {
            throw new AuthenticationException();
        }
        imageUploadService.deleteImage(mealId, IMAGE_FOLDER_NAME);
        homeMadeMealRepository.delete(mealId);
    }
}

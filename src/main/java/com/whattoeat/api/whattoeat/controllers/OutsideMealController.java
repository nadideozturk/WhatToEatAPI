package com.whattoeat.api.whattoeat.controllers;

import com.google.common.base.Strings;
import com.whattoeat.api.whattoeat.domain.OutsideMeal;
import com.whattoeat.api.whattoeat.domain.User;
import com.whattoeat.api.whattoeat.dto.OutsideMealDTO;
import com.whattoeat.api.whattoeat.exception.AuthenticationException;
import com.whattoeat.api.whattoeat.exception.NotFoundException;
import com.whattoeat.api.whattoeat.mapper.OutsideMealMapper;
import com.whattoeat.api.whattoeat.repository.OutsideMealRepository;
import com.whattoeat.api.whattoeat.repository.UserRepository;
import com.whattoeat.api.whattoeat.service.S3Service;
import com.whattoeat.api.whattoeat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/outsidemeals")
@RestController
public class OutsideMealController {
    @Autowired
    private OutsideMealRepository outsideMealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OutsideMealMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;

    private final String IMAGE_FOLDER_NAME = "whattoeat/outsidemeals";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OutsideMealDTO> getAll() {
        String userId = userService.getUserID();
        List<OutsideMealDTO> list = new ArrayList<OutsideMealDTO>();
        outsideMealRepository.findByUserId(userId).forEach(m -> {
            list.add(mapper.toDTO(m));
        });
        list.sort(Comparator.comparing(OutsideMealDTO::getLastEatenDate).reversed());
        return list;
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.GET)
    public OutsideMealDTO getMeal(@PathVariable String mealId) {
        String userId = userService.getUserID();
        OutsideMeal meal = outsideMealRepository.findOne(mealId);
        if (meal == null) {
            throw new NotFoundException();
        }
        if(!meal.getUserId().equals(userId)){
            throw new AuthenticationException();
        }
        return mapper.toDTO(meal);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createMeal(@RequestBody OutsideMealDTO outsideMealDto){
        String userId = userService.getUserID();
        OutsideMeal meal = mapper.createFromDTO(outsideMealDto);
        meal.setUserId(userId);
        User user = userRepository.findOne(userId);
        if (user != null) {
            meal.setCountry(user.getCountry());
            meal.setCity(user.getCity());
        }

        if (!Strings.isNullOrEmpty(outsideMealDto.getPhotoContent())) {
            final byte[] decodedImage = Base64.getMimeDecoder().decode(outsideMealDto.getPhotoContent());
            final String imageFilename = UUID.randomUUID().toString() + ".jpg";
            final String photoUrl = s3Service.uploadFile(decodedImage, "images/" + imageFilename);
            meal.setPhotoUrl(photoUrl);
        }

        if (meal.getLastEatenDate() == null) {
            meal.setLastEatenDate(new Date());
        }
        outsideMealRepository.save(meal);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public OutsideMealDTO updateMeal(@RequestBody OutsideMealDTO outsideMealDto){
        String userId = userService.getUserID();
        OutsideMeal meal = mapper.fromDTO(outsideMealDto);
        OutsideMeal existingMeal = outsideMealRepository.findOne(meal.getId());
        if(existingMeal == null){
            throw new NotFoundException();
        }
        if(!existingMeal.getUserId().equals(userId)){
            throw new AuthenticationException();
        }
        meal.setUserId(userId);

        if (!Strings.isNullOrEmpty(outsideMealDto.getPhotoContent())) {
            final byte[] decodedImage = Base64.getMimeDecoder().decode(outsideMealDto.getPhotoContent());
            final String imageFilename = UUID.randomUUID().toString() + ".jpg";
            final String photoUrl = s3Service.uploadFile(decodedImage, "images/" + imageFilename);
            meal.setPhotoUrl(photoUrl);
        }

        outsideMealRepository.save(meal);

        return mapper.toDTO(meal);
    }

    @RequestMapping(value= "/{mealId}", method = RequestMethod.DELETE)
    public void deleteMeal(@PathVariable String mealId){
        OutsideMeal meal = outsideMealRepository.findOne(mealId);
        String userId = userService.getUserID();
        if(meal == null){
            throw new NotFoundException();
        }
        if(!meal.getUserId().equals(userId)){
            throw new AuthenticationException();
        }
//        imageUploadService.deleteImage(mealId,IMAGE_FOLDER_NAME);
        outsideMealRepository.delete(mealId);
    }
    
}

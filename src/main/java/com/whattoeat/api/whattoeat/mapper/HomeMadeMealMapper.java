package com.whattoeat.api.whattoeat.mapper;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import com.whattoeat.api.whattoeat.dto.HomeMadeMealDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HomeMadeMealMapper {

    public HomeMadeMealDTO toDTO(HomeMadeMeal meal){
        return HomeMadeMealDTO.builder()
                .id(meal.getId())
                .name(meal.getName())
                .photoUrl(meal.getPhotoUrl())
                .durationInMinutes(meal.getDurationInMinutes())
                .lastEatenDate(meal.getLastEatenDate())
                .photoContent(" ")
                .catId(meal.getCatId())
                .recipe(meal.getRecipe())
                .tags(meal.getTags())
                .build();
    }

    public HomeMadeMeal createFromDTO(HomeMadeMealDTO homeMadeMealDTO){
        return HomeMadeMeal.builder()
                .id(UUID.randomUUID().toString())
                .name(homeMadeMealDTO.getName())
                .photoUrl(homeMadeMealDTO.getPhotoUrl())
                .durationInMinutes(homeMadeMealDTO.getDurationInMinutes())
                .lastEatenDate(homeMadeMealDTO.getLastEatenDate())
                .catId(homeMadeMealDTO.getCatId())
                .recipe(homeMadeMealDTO.getRecipe())
                .tags(homeMadeMealDTO.getTags())
                .build();
    }
    public HomeMadeMeal fromDTO(HomeMadeMealDTO homeMadeMealDTO){
        return HomeMadeMeal.builder()
                .id(homeMadeMealDTO.getId())
                .name(homeMadeMealDTO.getName())
                .photoUrl(homeMadeMealDTO.getPhotoUrl())
                .durationInMinutes(homeMadeMealDTO.getDurationInMinutes())
                .lastEatenDate(homeMadeMealDTO.getLastEatenDate())
                .catId(homeMadeMealDTO.getCatId())
                .recipe(homeMadeMealDTO.getRecipe())
                .tags(homeMadeMealDTO.getTags())
                .build();
    }
}

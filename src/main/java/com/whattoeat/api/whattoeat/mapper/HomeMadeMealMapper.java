package com.whattoeat.api.whattoeat.mapper;

import com.whattoeat.api.whattoeat.domain.HomeMadeMeal;
import com.whattoeat.api.whattoeat.dto.HomeMadeMealDTO;

public class HomeMadeMealMapper {

    public HomeMadeMealDTO toDTO(HomeMadeMeal meal){
        return HomeMadeMealDTO.builder()
                .id(meal.getId())
                .name(meal.getName())
                .photoUrl(meal.getPhotoUrl())
                .durationInMinutes(meal.getDurationInMinutes())
                .build();
    }

}

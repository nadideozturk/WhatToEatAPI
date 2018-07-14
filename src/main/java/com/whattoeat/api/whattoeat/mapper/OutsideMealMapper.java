package com.whattoeat.api.whattoeat.mapper;

import com.whattoeat.api.whattoeat.domain.OutsideMeal;
import com.whattoeat.api.whattoeat.dto.OutsideMealDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutsideMealMapper {
    public OutsideMealDTO toDTO(OutsideMeal meal){
        return OutsideMealDTO.builder()
                .id(meal.getId())
                .name(meal.getName())
                .photoUrl(meal.getPhotoUrl())
                .price(meal.getPrice())
                .lastEatenDate(meal.getLastEatenDate())
                .restaurantName(meal.getRestaurantName())
                .photoContent(" ")
                .build();
    }

    public OutsideMeal createFromDTO(OutsideMealDTO OutsideMealDTO){
        return OutsideMeal.builder()
                .id(UUID.randomUUID().toString())
                .name(OutsideMealDTO.getName())
                .photoUrl(OutsideMealDTO.getPhotoUrl())
                .price(OutsideMealDTO.getPrice())
                .lastEatenDate(OutsideMealDTO.getLastEatenDate())
                .restaurantName(OutsideMealDTO.getRestaurantName())
                .build();
    }
    public OutsideMeal fromDTO(OutsideMealDTO OutsideMealDTO){
        return OutsideMeal.builder()
                .id(OutsideMealDTO.getId())
                .name(OutsideMealDTO.getName())
                .photoUrl(OutsideMealDTO.getPhotoUrl())
                .price(OutsideMealDTO.getPrice())
                .lastEatenDate(OutsideMealDTO.getLastEatenDate())
                .restaurantName(OutsideMealDTO.getRestaurantName())
                .build();
    }
}
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
                .catId(meal.getCatId())
                .build();
    }

    public OutsideMeal createFromDTO(OutsideMealDTO outsideMealDTO){
        return OutsideMeal.builder()
                .id(UUID.randomUUID().toString())
                .name(outsideMealDTO.getName())
                .photoUrl(outsideMealDTO.getPhotoUrl())
                .price(outsideMealDTO.getPrice())
                .lastEatenDate(outsideMealDTO.getLastEatenDate())
                .restaurantName(outsideMealDTO.getRestaurantName())
                .catId(outsideMealDTO.getCatId())
                .build();
    }
    public OutsideMeal fromDTO(OutsideMealDTO outsideMealDTO){
        return OutsideMeal.builder()
                .id(outsideMealDTO.getId())
                .name(outsideMealDTO.getName())
                .photoUrl(outsideMealDTO.getPhotoUrl())
                .price(outsideMealDTO.getPrice())
                .lastEatenDate(outsideMealDTO.getLastEatenDate())
                .restaurantName(outsideMealDTO.getRestaurantName())
                .catId(outsideMealDTO.getCatId())
                .build();
    }
}
package com.whattoeat.api.whattoeat.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HomeMadeMealDTO {
    private String id;

    private String name;

    private String photoUrl;

    private Integer durationInMinutes;
}

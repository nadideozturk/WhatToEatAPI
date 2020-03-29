package com.whattoeat.api.whattoeat.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.whattoeat.api.whattoeat.domain.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;


@Builder
@Data
public class OutsideMealDTO {

    private String id;

    private String name;

    private String photoUrl;

    private String photoContent;

    private Double price;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastEatenDate;

    private String restaurantName;

    private String catId;

    private Set<Tag> tags;

    private String city;

    private String country;
}

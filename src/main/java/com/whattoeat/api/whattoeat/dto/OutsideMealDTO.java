package com.whattoeat.api.whattoeat.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


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
}

package com.whattoeat.api.whattoeat.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class HomeMadeMealDTO {
    private String id;

    private String name;

    private String photoUrl;

    private Integer durationInMinutes;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastEatenDate;
}

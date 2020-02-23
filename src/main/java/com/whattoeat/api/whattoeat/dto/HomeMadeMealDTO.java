package com.whattoeat.api.whattoeat.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.whattoeat.api.whattoeat.domain.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Builder
@Data
public class HomeMadeMealDTO {
    private String id;

    private String name;

    private String photoUrl;

    private Integer durationInMinutes;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastEatenDate;

    private String photoContent;

    private String catId;

    private String recipe;

    private Set<Tag> tags;
}

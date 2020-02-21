package com.whattoeat.api.whattoeat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Data
@Entity
public class HomeMadeMeal {

    @Id
    private String id;

    private String userId;

    private String name;

    private String photoUrl;

    private Integer durationInMinutes;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastEatenDate;

    private String catId;

    private String recipe;

    @Tolerate
    HomeMadeMeal() {}
}

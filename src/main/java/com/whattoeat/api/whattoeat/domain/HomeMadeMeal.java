package com.whattoeat.api.whattoeat.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Data
@Entity
public class HomeMadeMeal {

    @Id
    private String id;

    private String name;

    private String photoUrl;

    private Integer durationInMinutes;

    @Tolerate
    HomeMadeMeal() {}
}

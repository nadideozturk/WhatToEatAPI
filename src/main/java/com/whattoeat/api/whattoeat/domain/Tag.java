package com.whattoeat.api.whattoeat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@Entity
public class Tag {

    @Id
    private String id;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<HomeMadeMeal> homeMadeMeals = new HashSet<>();

    @Tolerate
    Tag() {}
}
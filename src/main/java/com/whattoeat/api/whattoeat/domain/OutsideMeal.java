package com.whattoeat.api.whattoeat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Builder
@Data
@Entity
public class OutsideMeal {
    @Id
    private String id;

    private String userId;

    private String name;

    private String photoUrl;

    private Double price;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastEatenDate;

    private String restaurantName;

    private String catId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "meal_tag",
            joinColumns = { @JoinColumn(name="meal_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name="tag_id", nullable = false, updatable = false)})
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    @Tolerate
    OutsideMeal() {}
}
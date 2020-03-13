package com.whattoeat.api.whattoeat.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Data
@Entity
public class User {
    @Id
    private String id;

    private boolean isPrivate;

    @Tolerate
    User() {}
}

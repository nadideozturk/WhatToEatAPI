package com.whattoeat.api.whattoeat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String id;

    @JsonProperty(value="isPrivate")
    private boolean isPrivate;

    private String photoUrl;

    private String city;

    private String country;
}

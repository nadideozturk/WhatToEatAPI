package com.whattoeat.api.whattoeat.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Builder
@Data
@Entity
@IdClass(CityCountry.CityCountryKey.class)
public class CityCountry {

    @Id
    private String city;

    @Id
    private String country;

    @Tolerate
    CityCountry() {}

    @Builder
    @Data
    public static class CityCountryKey implements Serializable {
        private String city;
        private String country;
    }

}

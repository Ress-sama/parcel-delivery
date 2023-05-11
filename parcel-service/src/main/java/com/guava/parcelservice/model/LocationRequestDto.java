package com.guava.parcelservice.model;

import lombok.Data;

@Data
public class LocationRequestDto {

    private Integer userId;
    private Float longitude;
    private Float latitude;

}

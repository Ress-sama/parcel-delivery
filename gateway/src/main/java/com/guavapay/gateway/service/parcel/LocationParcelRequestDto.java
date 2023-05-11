package com.guavapay.gateway.service.parcel;

import lombok.Data;

@Data
public class LocationParcelRequestDto {

    private Integer userId;
    private Float longitude;
    private Float latitude;

}

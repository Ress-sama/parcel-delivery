package com.guavapay.gateway.service.parcel;

import com.guavapay.gateway.model.LocationRequestDto;
import lombok.Data;

@Data
public class ParcelServiceRequestDto {

    private String details;
    private Integer userId;
    private LocationRequestDto startLocation;
    private LocationRequestDto targetLocation;

}

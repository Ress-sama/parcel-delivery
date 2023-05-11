package com.guavapay.gateway.model;

import lombok.Data;

@Data
public class ParcelRequestDto {

    private String details;
    private LocationRequestDto startLocation;
    private LocationRequestDto targetLocation;

}

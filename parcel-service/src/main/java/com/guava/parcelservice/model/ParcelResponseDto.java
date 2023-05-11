package com.guava.parcelservice.model;

import com.guava.parcelservice.common.ParcelStatus;
import com.guava.parcelservice.domain.Location;
import lombok.Data;

@Data
public class ParcelResponseDto {

    private Integer id;
    private String details;
    private ParcelStatus status;
    private Location startLocation;
    private Location targetLocation;
    private Integer userId;

}

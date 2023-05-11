package com.guava.parcelservice.model;

import com.guava.parcelservice.domain.Location;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParcelRequestDto {

    private String details;
    @NotNull
    private Integer userId;
    @NotNull
    private Location startLocation;
    @NotNull
    private Location targetLocation;

}

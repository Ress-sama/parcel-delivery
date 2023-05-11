package com.guava.deliveryservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryRequestDto {

    @NotNull
    private Integer parcelId;
    @NotNull
    private Integer courierId;

}

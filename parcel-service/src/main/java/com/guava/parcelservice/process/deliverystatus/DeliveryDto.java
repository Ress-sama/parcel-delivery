package com.guava.parcelservice.process.deliverystatus;

import lombok.Data;

@Data
public class DeliveryDto {

    private Integer courierId;
    private Integer parcelId;
    private DeliveryStatus status;

}

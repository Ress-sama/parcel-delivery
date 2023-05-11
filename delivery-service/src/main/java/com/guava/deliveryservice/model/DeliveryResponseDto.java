package com.guava.deliveryservice.model;

import com.guava.deliveryservice.common.DeliveryStatus;
import lombok.Data;

@Data
public class DeliveryResponseDto {

    private Integer id;
    private Integer courierId;
    private Integer parcelId;
    private DeliveryStatus status;

}

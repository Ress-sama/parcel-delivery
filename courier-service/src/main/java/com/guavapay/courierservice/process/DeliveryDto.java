package com.guavapay.courierservice.process;

import lombok.Data;

@Data
public class DeliveryDto {

    private Integer courierId;
    private Integer parcelId;
    private DeliveryStatus status;

}

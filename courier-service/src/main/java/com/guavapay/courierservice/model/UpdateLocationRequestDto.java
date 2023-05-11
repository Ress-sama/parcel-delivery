package com.guavapay.courierservice.model;

import lombok.Data;

@Data
public class UpdateLocationRequestDto {

    private Integer courierId;
    private Float longitude;
    private Float latitude;

}

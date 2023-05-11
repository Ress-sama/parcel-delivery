package com.guavapay.gateway.service.courier;

import lombok.Data;

@Data
public class UpdateLocationCourierRequestDto {

    private Integer courierId;
    private Float longitude;
    private Float latitude;

}

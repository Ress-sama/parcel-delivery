package com.guavapay.courierservice.model;

import lombok.Data;

@Data
public class UpdateCourierStatusRequestDto {

    private Integer courierId;
    private boolean available;

}

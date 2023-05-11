package com.guava.parcelservice.common;

import lombok.Data;

@Data
public class PaginationRequest {

    private int page = 0;
    private int size = 15;

}

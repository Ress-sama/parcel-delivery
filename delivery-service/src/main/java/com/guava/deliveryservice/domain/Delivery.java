package com.guava.deliveryservice.domain;

import com.guava.deliveryservice.common.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer parcelId;
    @NotNull
    private Integer courierId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}

package com.guava.parcelservice.domain;

import com.guava.parcelservice.common.ParcelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    private String details;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "start_location_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "start_location_latitude"))
    })
    private Location startLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "target_location_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "target_location_latitude"))
    })
    private Location targetLocation;
    private Integer userId;

}

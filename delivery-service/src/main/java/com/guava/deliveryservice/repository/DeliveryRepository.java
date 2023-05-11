package com.guava.deliveryservice.repository;

import com.guava.deliveryservice.domain.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    @Query(value = "select count(d) = 0 from deliveries d " +
            "where d.courier_id = :courierId and d.status = 'ON_COURIER' ",nativeQuery = true)
    boolean canCourierDeliverParcel(Integer courierId); // CAN CHANGE COURIER DELIVERY COUNT. MAYBE IN THE FUTURE WILL BE CHANGE COURIER DELIVERY LIMIT

    @Query(value = "select count(d) = 0 from deliveries d " +
            "where d.parcel_id = :parcelId and d.status = 'ON_COURIER' ",nativeQuery = true)
    boolean isParcelOnDelivery(Integer parcelId);

    Page<Delivery> findAllByCourierId(Integer courierId, Pageable pageable);
}

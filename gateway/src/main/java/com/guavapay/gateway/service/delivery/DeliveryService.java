package com.guavapay.gateway.service.delivery;

import com.guavapay.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${service.delivery.name}", url = "${service.delivery.url}",
        configuration = FeignClientConfiguration.class)
public interface DeliveryService {

    @PostMapping("/v1/deliveries")
    Object create(@RequestBody DeliveryServiceRequestDto deliveryRequest);

    @GetMapping("/v1/deliveries/couriers/{id}/deliveries")
    Object getDeliveriesByCourierId(@PathVariable Integer id, @RequestParam int page, @RequestParam int size);

    @PutMapping("/v1/deliveries/{id}/change-status")
    void changeStatus(@PathVariable Integer id, @RequestParam DeliveryStatus status);

    @GetMapping("/v1/deliveries")
    Object getAll(@RequestParam int page, @RequestParam int size);

    @PutMapping("/v1/deliveries/{id}/cancel")
    void cancel(@PathVariable Integer id);
}

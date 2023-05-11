package com.guava.deliveryservice.mapper;

import com.guava.deliveryservice.common.DeliveryStatus;
import com.guava.deliveryservice.domain.Delivery;
import com.guava.deliveryservice.model.DeliveryRequestDto;
import com.guava.deliveryservice.model.DeliveryResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DeliveryMapper {

    public Delivery toEntity(DeliveryRequestDto deliveryRequestDto) {
        if (Objects.isNull(deliveryRequestDto)) return null;

        Delivery delivery = new Delivery();
        delivery.setParcelId(deliveryRequestDto.getParcelId());
        delivery.setCourierId(deliveryRequestDto.getCourierId());
        return delivery;
    }

    public DeliveryResponseDto toDto(Delivery delivery) {
        if (Objects.isNull(delivery)) return null;

        DeliveryResponseDto deliveryResponseDto = new DeliveryResponseDto();
        deliveryResponseDto.setId(delivery.getId());
        deliveryResponseDto.setParcelId(delivery.getParcelId());
        deliveryResponseDto.setCourierId(delivery.getCourierId());
        deliveryResponseDto.setStatus(delivery.getStatus());
        return deliveryResponseDto;

    }

    public List<DeliveryResponseDto> toDto(List<Delivery> deliveries) {
        if (deliveries.isEmpty()) return null;
        List<DeliveryResponseDto> deliveryResponseDtoList = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            deliveryResponseDtoList.add(toDto(delivery));
        }
        return deliveryResponseDtoList;
    }

}

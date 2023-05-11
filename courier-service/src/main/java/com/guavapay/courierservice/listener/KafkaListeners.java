package com.guavapay.courierservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.courierservice.entity.Location;
import com.guavapay.courierservice.entity.User;
import com.guavapay.courierservice.entity.UserLocation;
import com.guavapay.courierservice.model.UpdateLocationRequestDto;
import com.guavapay.courierservice.process.BaseDeliveryStatusProcess;
import com.guavapay.courierservice.process.BaseDeliveryStatusProcessFactory;
import com.guavapay.courierservice.process.DeliveryDto;
import com.guavapay.courierservice.repository.UserLocationRepository;
import com.guavapay.courierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final BaseDeliveryStatusProcessFactory deliveryStatusProcessFactory;

    @SneakyThrows
    @KafkaListener(topics = "update-location", groupId = "location-updaters")
    protected void updateLocationListener(String data) {
        UpdateLocationRequestDto updateLocationRequestDto = objectMapper.readValue(data,
                UpdateLocationRequestDto.class);
        UserLocation userLocation = new UserLocation();
        User user = userRepository.findById(updateLocationRequestDto.getCourierId()).orElseThrow();
        userLocation.setUser(user);
        userLocation.setLocation(new Location(updateLocationRequestDto.getLongitude(),
                updateLocationRequestDto.getLatitude()));
        userLocationRepository.save(userLocation);
    }

    @SneakyThrows
    @KafkaListener(topics = "delivery_status_updated", groupId = "delivery_status_updaters")
    protected void updateCourierStatusListener(String data) {
        DeliveryDto deliveryDto = objectMapper.readValue(data, DeliveryDto.class);
        BaseDeliveryStatusProcess process = deliveryStatusProcessFactory.create(deliveryDto.getStatus());
        process.tryProcess(deliveryDto);
    }

}

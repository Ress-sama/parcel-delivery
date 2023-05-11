package com.guava.deliveryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guava.deliveryservice.common.DeliveryStatus;
import com.guava.deliveryservice.common.PaginationRequest;
import com.guava.deliveryservice.domain.Delivery;
import com.guava.deliveryservice.error.EntityNotFoundException;
import com.guava.deliveryservice.error.ProcessIsNotPossibleException;
import com.guava.deliveryservice.mapper.DeliveryMapper;
import com.guava.deliveryservice.model.DeliveryRequestDto;
import com.guava.deliveryservice.model.DeliveryResponseDto;
import com.guava.deliveryservice.process.BaseDeliveryProcess;
import com.guava.deliveryservice.process.DeliveryProcessFactory;
import com.guava.deliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryProcessFactory deliveryProcessFactory;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public Page<DeliveryResponseDto> getAll(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Delivery> deliveries = deliveryRepository.findAll(pageable);
        return deliveries.map(deliveryMapper::toDto);
    }

    public DeliveryResponseDto getById(Integer id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Delivery.class, id));
        return deliveryMapper.toDto(delivery);
    }

    public Page<DeliveryResponseDto> getPageByCourierId(Integer courierId,
                                                        PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Delivery> deliveries = deliveryRepository.findAllByCourierId(courierId, pageable);
        return deliveries.map(deliveryMapper::toDto);
    }

    @SneakyThrows
    public Integer create(DeliveryRequestDto deliveryRequestDto) {
        validateRequest(deliveryRequestDto);
        Delivery delivery = deliveryMapper.toEntity(deliveryRequestDto);
        BaseDeliveryProcess process = deliveryProcessFactory.create(DeliveryStatus.WAITING_FOR_COURIER_RESPONSE);
        process.tryProcess(delivery);
        return delivery.getId();
    }

    private void validateRequest(DeliveryRequestDto deliveryRequestDto) {
        checkCourierIsAvailableToDelivery(deliveryRequestDto.getCourierId());
        checkParcelIsAvailableToDelivery(deliveryRequestDto.getParcelId());
    }

    private void checkCourierIsAvailableToDelivery(Integer courierId) {
        if (deliveryRepository.canCourierDeliverParcel(courierId)) return;
        throw new ProcessIsNotPossibleException("Courier with [" + courierId + "] id is not available for delivery");
    }

    private void checkParcelIsAvailableToDelivery(Integer parcelId) {
        if (deliveryRepository.isParcelOnDelivery(parcelId)) return;
        throw new ProcessIsNotPossibleException("Parcel with [" + parcelId + "] id is already on delivery");
    }

    public void updateStatus(Integer id, DeliveryStatus deliveryStatus) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Delivery.class, id));
        BaseDeliveryProcess process = deliveryProcessFactory.create(deliveryStatus);
        process.tryProcess(delivery);
    }
}

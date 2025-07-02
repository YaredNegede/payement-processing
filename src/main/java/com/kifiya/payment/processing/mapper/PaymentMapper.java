package com.kifiya.payment.processing.mapper;

import com.kifiya.payment.processing.core.entities.Payment;
import com.kifiya.payment.processing.in.dto.PaymentRequestDto;
import com.kifiya.payment.processing.in.dto.PaymentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Payment toEntity(PaymentRequestDto paymentRequestDTO);

    PaymentResponseDTO toResponseDTO(Payment payment);
}

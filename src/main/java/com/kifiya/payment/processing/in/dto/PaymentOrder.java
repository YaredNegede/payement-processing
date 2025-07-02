package com.kifiya.payment.processing.in.dto;

import lombok.Data;

@Data
public class PaymentOrder {

    private String provider;

    private String orderNumber;

    private PaymentRequestDto paymentRequestDto;
}

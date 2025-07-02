package com.kifiya.payment.processing.in.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kifiya.payment.processing.in.dto.PaymentOrder;

import java.util.UUID;

public interface Provider {

    default String generateOrderNumber(){
        return UUID.randomUUID().toString();
    }

    String ingestPayment(PaymentOrder paymentOrder) throws JsonProcessingException;

    boolean updatePayment(PaymentOrder updatedPaymentOrder) throws JsonProcessingException;
}

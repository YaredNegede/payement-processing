package com.kifiya.payment.processing.out.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kifiya.payment.processing.in.dto.PaymentOrder;

public interface GateWayProvider {

    String ingestPayment(String paymentOrder) throws JsonProcessingException;

    boolean updatePayment(String updatedPaymentOrder) throws JsonProcessingException;

}

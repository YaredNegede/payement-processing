package com.kifiya.payment.processing.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kifiya.payment.processing.in.dto.PaymentOrder;
import com.kifiya.payment.processing.in.dto.PaymentStatusResponse;
import com.kifiya.payment.exceptions.DuplicatePaymentException;

import java.util.List;

public interface PaymentProcessing {

    boolean cancelPayment(String paymentId);

    boolean updatePayment(String paymentId, PaymentOrder updatedPaymentOrder) throws JsonProcessingException;

    List<PaymentOrder> getAllPaymentOrders();

    PaymentOrder getPaymentOrder(String paymentId);

    String ingestPayment(PaymentOrder paymentOrder) throws DuplicatePaymentException, JsonProcessingException;

    PaymentStatusResponse getPaymentStatus(String paymentId);

}

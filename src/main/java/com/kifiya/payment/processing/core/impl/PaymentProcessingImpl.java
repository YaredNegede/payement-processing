package com.kifiya.payment.processing.core.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kifiya.payment.processing.core.PaymentProcessing;
import com.kifiya.payment.processing.in.dto.PaymentStatusResponse;
import com.kifiya.payment.processing.in.dto.PaymentOrder;
import com.kifiya.payment.exceptions.DuplicatePaymentException;
import com.kifiya.payment.processing.in.provider.PaymentProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PaymentProcessingImpl implements PaymentProcessing {

    /**
     *
     */
    private final PaymentProvider paymentProvider;

    /**
     *
     * @param paymentProvider
     */
    public PaymentProcessingImpl(PaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    @Override
    public boolean cancelPayment(String paymentId) {
        return false;
    }

    @Override
    public boolean updatePayment(String paymentId, PaymentOrder updatedPaymentOrder) throws JsonProcessingException {
        return this.paymentProvider.getProvider().get(updatedPaymentOrder.getProvider()).updatePayment(updatedPaymentOrder);
    }

    @Override
    public List<PaymentOrder> getAllPaymentOrders() {
        return List.of();
    }

    @Override
    public PaymentOrder getPaymentOrder(String paymentId) {
        return null;
    }

    @Override
    public String ingestPayment(PaymentOrder paymentOrder) throws DuplicatePaymentException, JsonProcessingException {
        return this.paymentProvider.getProvider().get(paymentOrder.getProvider()).ingestPayment(paymentOrder);
    }

    @Override
    public PaymentStatusResponse getPaymentStatus(String paymentId) {
        return null;
    }
}

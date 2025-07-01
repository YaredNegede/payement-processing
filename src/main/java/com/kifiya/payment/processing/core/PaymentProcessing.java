package com.kifiya.payment.processing.core;

import com.kifiya.payment.processing.in.dto.PaymentOrder;
import com.kifiya.payment.processing.in.dto.PaymentStatusResponse;
import com.kifiya.payment.exceptions.DuplicatePaymentException;

import java.util.List;

public interface PaymentProcessing {

    boolean cancelPayment(String paymentId);

    boolean updatePayment(String paymentId, PaymentOrder updatedPaymentOrder);

    List<PaymentOrder> getAllPaymentOrders();

    PaymentOrder getPaymentOrder(String paymentId);

    String ingestPayment(PaymentOrder paymentOrder) throws DuplicatePaymentException;

    PaymentStatusResponse getPaymentStatus(String paymentId);

}

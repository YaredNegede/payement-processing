package com.kifiya.payment.processing.out.provider;

import com.kifiya.payment.processing.in.dto.PaymentOrder;

public interface PaymentGateway {
    String ingestPayment(PaymentOrder paymentOrder);

    boolean updatePayment(PaymentOrder updatedPaymentOrder);
}

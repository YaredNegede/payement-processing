package com.kifiya.payment.processing.out.provider;

import com.kifiya.payment.processing.in.dto.PaymentOrder;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CBEPaymentGateway implements PaymentGateway {
    /**
     *
     * @param paymentOrder
     * @return
     */
    @Override
    public String ingestPayment(PaymentOrder paymentOrder) {
        return "";
    }

    /**
     *
     * @param updatedPaymentOrder
     * @return
     */
    @Override
    public boolean updatePayment(PaymentOrder updatedPaymentOrder) {
        return false;
    }
}

package com.kifiya.payment.processing.out.provider;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class PaymentProvider {

    Map<String ,PaymentGateway> provider;

    public PaymentProvider(Map<String, PaymentGateway> provider) {
        this.provider = provider;
    }

}

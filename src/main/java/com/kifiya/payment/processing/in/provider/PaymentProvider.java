package com.kifiya.payment.processing.in.provider;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class PaymentProvider {

    Map<String , Provider> provider;

    public PaymentProvider(Map<String, Provider> provider) {
        this.provider = provider;
    }

}

package com.kifiya.payment.processing.in.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.payment.processing.in.dto.PaymentOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@Component
public class CBEProvider implements Provider {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC_UPDATE = "cbe-update";

    public static final String TOPIC_INGEST = "cbe-ingest";

    private final ObjectMapper mapper;

    public CBEProvider(final KafkaTemplate<String, String> kafkaTemplate, final ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    /**
     *
     * @param paymentOrder
     * @return
     */
    @Override
    public String ingestPayment(final PaymentOrder paymentOrder) throws JsonProcessingException {
        String orderNumber = generateOrderNumber();
        paymentOrder.setOrderNumber(orderNumber);
        kafkaTemplate.send(TOPIC_INGEST,mapper.writeValueAsString(paymentOrder));
        return orderNumber;
    }

    /**
     *
     * @param updatedPaymentOrder
     * @return
     */
    @Override
    public boolean updatePayment(final PaymentOrder updatedPaymentOrder) throws JsonProcessingException {
        kafkaTemplate.send(TOPIC_UPDATE,mapper.writeValueAsString(updatedPaymentOrder));
        return false;
    }
}
